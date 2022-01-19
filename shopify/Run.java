/** Shopify Backend Challenge - Summer 2022
 * 
 * File: Run.java
 * Due Date: Wednesday, January 19th, 2022
 * 
 * @author Jasjot Gill
 * @version 1.0
 */

package shopify;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;


public class Run {

    private static Scanner keyboard = new Scanner(System.in);

    /** gets user input for the stock count of some inventory item
     * 
     * @return the number of items of some inventory
     */
    public static int getNumItems() {

        int numItems;

        //loop until valid input

        while (true) {

            System.out.print("Items in Stock: ");
            numItems = keyboard.nextInt();

            if (numItems > 1000 || numItems < 0) {

                System.out.println("ERROR: INVENTORY STOCK MUST RANGE FROM 0-1000 INCLUSIVE.");
            }
            else {

                break;
            }
        }

        //throw away unconsumed \n after nextInt()

        keyboard.nextLine();
        return numItems;
    }

    /** gets user input for the name of an inventory item
     * 
     * @param map_inv is a map of existing inventory names and items
     * @return the inventory items name
     */
    public static String getInventoryName(HashMap<String, Inventory> map_inv) {

        String item = "";

        //loop until unexisting item is named

        while (true) {

            //toUpperCase used for comparisions of keys in map

            System.out.print("Inventory Name: ");
            item = keyboard.nextLine().toUpperCase();

            if (!map_inv.containsKey(item)) {

                break;
            }

            System.out.println("ERROR: INVENTORY NAME ALREADY EXISTS.");
        }

        return item;
    }

    /** gets city name for an inventory items warehouse
     * 
     * @param map_whs is a map of warehouse cities and warehouse objects
     * @return city of new warehouse
     */
    public static String getWarehouseName(HashMap<String, Warehouse> map_whs) {

        String city = "";

        //print existing warehouses for user to see

        System.out.println("\nAVAILABLE WAREHOUSES:\n" + map_whs.keySet().toString() + "\n");
        
        //loop until existing warehouse is named

        while (!map_whs.containsKey(city)) {

            System.out.print("Warehouse City: ");
            city = keyboard.nextLine().toUpperCase();

            if (!map_whs.containsKey(city)) {

                System.out.println("ERROR: WAREHOUSE LOCATION DOES NOT EXIST.");
            }
        }

        return city;
    }
    
    /** allows the editing of existin inventory items
     * 
     * @param map_whs is a map of warehouse cities and warehouse objects
     * @param map_inv is a map of names for inventory items and inventory objects
     * @return -1 for failure to edit and 1 for successful edit
     */
    public static int editInventory(HashMap<String, Warehouse> map_whs, HashMap<String, Inventory> map_inv) {

        printMenu2();
        int choice = -1;

        //break out if user enters non integer input

        try {

            System.out.print("\nChoice: ");
            choice = keyboard.nextInt();
        }
        catch (Exception e) {

            //throw away unconsumed \n after nextInt()

            keyboard.nextLine();
            System.out.println("ERROR: SOMETHING WENT WRONG.");
            return -1;
        }

        //break out if user inputs incorrect choice

        if (choice < 1 || choice > 3) {

            System.out.println("ERROR: INCORRECT INPUT.");
            return -1;
        }

        //throw away unconsumed \n after nextInt()

        keyboard.nextLine();
        System.out.print("Inventory Item For Edit: ");
        String inv = keyboard.nextLine().toUpperCase();

        //break out if the inventory item does not exist

        if (!map_inv.containsKey(inv)) {

            System.out.println("ERROR: INVENTORY ITEM DOES NOT EXIST");
            return -1;
        }

        if (choice == 1) {

            //edit inventory name in case of mispelled inventory

            String name = getInventoryName(map_inv);
            map_inv.get(inv).setName(name);

            //change the key in HashMap

            Inventory temp_inv = map_inv.get(inv);
            map_inv.remove(inv);
            map_inv.put(temp_inv.getName(), temp_inv);
            return 1;
        }
        else if (choice == 2) {

            if (map_whs.size() < 2) {

                System.out.println("ERROR: NEED AT LEAST 2 WAREHOUSES FOR THIS OPTION.");
                return -1;
            }

            String whs_name = getWarehouseName(map_whs);

            //remove object from old warehouse, set new warehouse, and add object to new warehouse

            map_whs.get(map_inv.get(inv).getWarehouse()).inventory.remove(map_inv.get(inv));
            map_whs.get(map_inv.get(inv).getWarehouse()).numInventory--;
            map_inv.get(inv).setWarehouse(whs_name);
            map_whs.get(map_inv.get(inv).getWarehouse()).addInventory(map_inv.get(inv));
            return 1;
        }
        else if (choice == 3) {

            int numItems = getNumItems();
            map_inv.get(inv).setNumItems(numItems);
            return 1;
        }

        return -1;
    }

    /** deletes an inventory item
     * 
     * @param map_whs is a map of warehouse cities and warehouse objects
     * @param map_inv is a map of names for inventory items and inventory objects
     * @return -1 for failure and 1 for successful deletion
     */
    public static int deleteInventory(HashMap<String, Warehouse> map_whs, HashMap<String, Inventory> map_inv) {
        
        System.out.print("Inventory Name For Deletion: ");
        String delete = keyboard.nextLine().toUpperCase();
            
        if (map_inv.get(delete) != null) {

            //Facility exists if inventory exists therefore diretly delete

            map_whs.get(map_inv.get(delete).getWarehouse()).inventory.remove(map_inv.get(delete));
            map_whs.get(map_inv.get(delete).getWarehouse()).numInventory--;
            map_inv.remove(delete);
            return 1;
        }
        
        return -1;
    }

    /** prints all data in readable format
     * 
     * @param map_whs is a map of warehouse cities and warehouse objects
     * @param map_inv is a map of names for inventory items and inventory objects
     */
    public static void printAllData(HashMap<String, Warehouse> map_whs, HashMap<String, Inventory> map_inv) {

        //call methods from classes in loop to print object data

        if (map_whs.size() != 0) {

            System.out.println("\nFACILITIES:");
        }

        for (String key: map_whs.keySet()) {

            map_whs.get(key).printWarehouseData();
        }

        if (map_inv.size() != 0) {
            
            System.out.println("\nINVENTORY:");
        }

        for (String key: map_inv.keySet()) {

            map_inv.get(key).printInventory();
        }
    }

    /** creates an inventory object and adds it to the inventory map and the warehouses HashSet
     * 
     * @param map_whs is a map of warehouse cities and warehouse objects
     * @param map_inv is a map of names for inventory items and inventory objects
     */
    public static void createInventory(HashMap<String, Warehouse> map_whs, HashMap<String, Inventory> map_inv) {

        //gather data for new inventory object

        String item = getInventoryName(map_inv);
        String city = getWarehouseName(map_whs);
        int numItems = getNumItems();

        //create object and add it to the inventory map and the correct warehouses HashSet of inventory

        Inventory temp_inv = new Inventory(item, city, numItems);
        map_inv.put(temp_inv.getName(), temp_inv);
        map_whs.get(temp_inv.getWarehouse()).addInventory(temp_inv);
    }

    /** creates a warehouse object and adds it to the map
     * 
     * @param map_whs is a map of warehouse cities and warehouse objects
     */
    public static void createWarehouse(HashMap<String, Warehouse> map_whs) {

        String city = "";

        //loop until unexisting unexisting city warehouse is named

        while (true) {

            System.out.print("Warehouse City: ");
            city = keyboard.nextLine().toUpperCase();

            if (!map_whs.containsKey(city)) {

                break;
            }

            System.out.println("ERROR: WAREHOUSE ALREADY EXISTS.");
        }

        //create warehouse object
        
        Warehouse temp_whs = new Warehouse(city);
        map_whs.put(temp_whs.getCity(), temp_whs);      
    }

    /** prints a menu
     * 
     */
    public static void printMenu() {

        System.out.println("\nWhat would you like to do?");
        System.out.println("    0. Exit Program");
        System.out.println("    1. Create Warehouse");
        System.out.println("    2. Create Inventory");
        System.out.println("    3. Edit Inventory");
        System.out.println("    4. Delete Inventory");
        System.out.println("    5. Print List\n");
    }

    /** prints a menu for editing
     * 
     */
    public static void printMenu2() {

        System.out.println("\nWhat would you like to edit?");
        System.out.println("    1. Inventory Name");
        System.out.println("    2. Inventory Warehouse");
        System.out.println("    3. Inventory stock");
    }
    
    /** main function that runs the program
     * 
     * @param args
     */
    public static void main(String[] args) {

        //HashMap for object storage

        HashMap<String, Warehouse> map_whs = new HashMap<>();
        HashMap<String, Inventory> map_inv = new HashMap<>();

        int input = -1;

        //loop until the user wants to exit

        while (input != 0) {

            printMenu();
            System.out.print("Choice: ");

            //try-catch terminates program if unexpected input is written

            try {
                
                input = keyboard.nextInt();
            }
            catch (Exception e){

                System.out.println("ERROR: SOMETHING WENT WRONG.");
                break;
            }

            //throw away unconsumed \n after nextInt()

            keyboard.nextLine();

            //execute correct functionality based on user input.
            
            if (input == 1) {

                createWarehouse(map_whs);
                System.out.println("WAREHOUSE CREATED.");
            }
            else if (input == 2) {

                if (map_whs.size() == 0) {

                    System.out.println("ERROR: WAREHOUSE FACILITY MUST EXIST TO CREATE INVENTORY.");
                    continue;
                }

                createInventory(map_whs, map_inv);
                System.out.println("INVENTORY CREATED");
            }
            else if (input == 3) {

                if (map_inv.size() == 0) {

                    System.out.println("ERROR: CREATE INVENTORY BEFORE EDITING.");
                    continue;
                } 

                int check = editInventory(map_whs, map_inv);

                if (check == -1) {

                    System.out.println("INVENTORY NOT EDITED.");
                }
                else {

                    System.out.println("INVENTORY EDITED.");
                }
            }
            else if (input == 4) {

                if (map_inv.size() == 0) {

                    System.out.println("ERROR: CREATE INVENTORY BEFORE DELETING.");
                    continue;
                }

                int check = deleteInventory(map_whs, map_inv);

                if (check == -1) {

                    System.out.println("ERROR: INVENTORY ITEM DOES NOT EXIST.");
                }
                else {

                    System.out.println("INVENTORY DELETED.");
                }
            }
            else if (input == 5) {

                printAllData(map_whs, map_inv);
            }
        }

        //close the keyboard

        keyboard.close();
    }
}