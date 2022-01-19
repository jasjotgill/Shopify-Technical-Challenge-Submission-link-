package shopify;

import java.util.HashSet;

public class Warehouse {

    private static int NEXT_WAREHOUSE_ID = 1001;

    private String                  city;
    protected HashSet<Inventory>    inventory;
    protected int                   numInventory;
    private int                     id;
    
    public Warehouse(String c) {

        city = c;
        inventory = new HashSet<>();
        numInventory = 0;
        id = NEXT_WAREHOUSE_ID++;
    }
    
    public void addInventory(Inventory inv) {

        inventory.add(inv);
        numInventory++;
    }

    public String getCity() {

        return city;
    }

    public int getNumInventory() {

        return numInventory;
    }

    public int getId() {

        return id;
    }

    public void printWarehouseData() {

        System.out.println("------------------------------------------------------------------------");

        String output = String.format("\nCity: %-20s | Inventory Items: %-5d | Warehouse ID: %d", this.city, this.numInventory, this.id);
        System.out.println(output);
        printWarehouseInventory();

        System.out.println("------------------------------------------------------------------------");
    }

    public void printWarehouseInventory() {

        System.out.println("\nWAREHOUSE INVENTORY ITEMS:");
        String output;

        for (Inventory inv: this.inventory) {

            output = String.format("%-20s", inv.getName());
            System.out.println(output);
        }

        System.out.println();
    }
}