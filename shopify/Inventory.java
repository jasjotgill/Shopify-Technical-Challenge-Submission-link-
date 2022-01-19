package shopify;

public class Inventory {

    private static int NEXT_INVENTORY_ID = 1001;

    private String  name;
    private String  whs;
    private int     numItems;
    private int     id;

    public Inventory(String inv_name, String whs_name, int num) {

        name = inv_name;
        whs = whs_name;
        numItems = num;
        id = NEXT_INVENTORY_ID++;
    }

    public void setName(String inv_name) {

        this.name = inv_name;
    }

    public void setWarehouse(String whs_name) {

        this.whs = whs_name;
    }

    public void setNumItems(int num) {

        this.numItems = num;
    }

    public String getName() {

        return name;
    }

    public String getWarehouse() {

        return whs;
    }

    public int getNumItems() {

        return numItems;
    }

    public int getId() {

        return id;
    }

    public void printInventory() {

        String output = String.format("Inventory Item: %-20s | Inventory Warehouse: %-20s | In Stock: %4d | Inventory ID: %d", this.name, this.whs, this.numItems, this.id);
        System.out.println(output);
    }
}