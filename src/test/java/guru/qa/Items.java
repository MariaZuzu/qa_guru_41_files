package guru.qa;

import java.util.List;

public class Items {

    private String billId;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String model) {
        this.billId = billId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(List<String> billInfo) {
        this.billInfo = billInfo;
    }

    private String name;

    private List<String> billInfo;

}


//{
//        "id": "552397078380755968",
//        "type": "vehicleV1",
//        "name": "Авто 2",
//        "items": {
//        "billId": "27850139720534546749",
//        "name": "Штрафы",
//        "billInfo": ["lastUpdateDate", "amount", "currency"]
//        }
//        }