package guru.qa;

public class Fines {
    private Long id;

    private String type;

    private String name;

    private Items items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}




//{
//        "id": "552397078380755968",
//        "type": "vehicleV1",
//        "name": "Авто 2",
//        "items": {
//        "billId": "27850139720534546749",
//        "name": "Штрафы",
//        "billInfo": [
//        "lastUpdateDate",
//        "amount",
//        "currency"
//        ]
//        }
//        }