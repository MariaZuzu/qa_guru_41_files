package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.apache.poi.ss.usermodel.WorkbookFactory.create;
import static org.junit.jupiter.api.Assertions.*;

public class SelenideFilesTest {

        private ClassLoader cl = SelenideFilesTest.class.getClassLoader();

        @Test
        void csvSelenideFilesTest() throws Exception {
            try (ZipInputStream zis = new ZipInputStream(
                    cl.getResourceAsStream("testarkhiv1.zip"))) {
                ZipEntry entry = zis.getNextEntry();

                assertNotNull(entry);
                boolean hasRequiredFile = false;
                do {

                    String[] fileData = entry.getName().split("\\.");
                    String fileType = fileData[fileData.length - 1];

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    zis.transferTo(baos);
                    byte[] fileBytes = baos.toByteArray();
                    ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes);

                    if (!"csv".equals(fileType)) {
                        continue;
                    }
                    hasRequiredFile = true;
                    try (CSVReader csvReader = new CSVReaderBuilder(
                            new InputStreamReader(bais))
                            .withCSVParser(new RFC4180ParserBuilder()
                                    .withSeparator(';')
                                    .build())
                            .build()) {

                        List<String[]> data = csvReader.readAll();
                        assertEquals(3, data.size());
                        Assertions.assertArrayEquals(
                                new String[]{"first_name", "last_name", "age"},
                                data.get(0)
                        );
                        Assertions.assertArrayEquals(
                                new String[]{"Liam", "Williams", "49"},
                                data.get(1)
                        );
                        Assertions.assertArrayEquals(
                                new String[]{"Jane", "Williams", "71"},
                                data.get(2)
                        );
                    }
                } while ((entry = zis.getNextEntry()) != null);
                assertTrue(hasRequiredFile);

            }
        }

        @Test
        void pdfSelenideFilesTest() throws Exception {
            try (ZipInputStream zis = new ZipInputStream(
                    cl.getResourceAsStream("testarkhiv1.zip"))) {
                ZipEntry entry = zis.getNextEntry();

                assertNotNull(entry);

                boolean hasRequiredFile = false;

                do {

                    String[] fileData = entry.getName().split("\\.");
                    String fileType = fileData[fileData.length - 1];

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    zis.transferTo(baos);
                    byte[] fileBytes = baos.toByteArray();
                    ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes);

                    if (!"pdf".equals(fileType)) {
                        continue;
                    }

                    hasRequiredFile = true;

                    try (PDDocument document = PDDocument.load(bais)) {

                        PDFTextStripper pdfStripper = new PDFTextStripper();
                        String text = pdfStripper.getText(document);

                        assertTrue(text.contains("Тестовый PDF-документ"));
                    }
                } while ((entry = zis.getNextEntry()) != null);
                assertTrue(hasRequiredFile);

            }
        }

        @Test
        void xlsxSelenideFilesTest() throws Exception {
            try (ZipInputStream zis = new ZipInputStream(
                    cl.getResourceAsStream("testarkhiv1.zip"))) {
                ZipEntry entry = zis.getNextEntry();

                assertNotNull(entry);

                boolean hasRequiredFile = false;

                do {

                    String[] fileData = entry.getName().split("\\.");
                    String fileType = fileData[fileData.length - 1];

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    zis.transferTo(baos);
                    byte[] fileBytes = baos.toByteArray();
                    ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes);

                    if (!"xlsx".equals(fileType)) {
                        continue;
                    }
                    hasRequiredFile = true;
                    try (Workbook workbook = create(bais)) {

                        Sheet sheet = workbook.getSheet("Зарплата");
                        Row row = sheet.getRow(1);
                        Cell cell = row.getCell(3);

                        String value = cell.getStringCellValue();

                        assertEquals("40817810896823771961", value);
                    }
                } while ((entry = zis.getNextEntry()) != null);
                assertTrue(hasRequiredFile);
            }
        }

    void jsonSelenideFilesTest() throws Exception {
        Path path;
        path = Path.of(getClass().getClassLoader().getResource("carfines.json").toURI());
        String json = Files.readString(path);

            ObjectMapper mapper = new ObjectMapper();
            Fines fines = mapper.readValue(json, Fines.class);

            assertEquals(552397078380755968L, fines.getId());
            assertEquals("vehicleV1", fines.getType());
            assertEquals("Авто 2", fines.getName());

            assertNotNull(fines.getItems());
            assertEquals("27850139720534546749", fines.getItems().getBillId());
            assertEquals("Штрафы", fines.getItems().getName());
            assertEquals(3, fines.getItems().getBillInfo().size());
            assertTrue(fines.getItems().getBillInfo().contains("lastUpdateDate"));
        }
    }
