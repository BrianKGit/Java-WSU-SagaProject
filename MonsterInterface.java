
import java.util.ArrayList;

public interface MonsterInterface {
    
	/**
	 * 
	 * @return Returns the name
	 */
    public String getName();
    
    /**
     * Sets the name
     * @param name New name
     */
    public void setName(String name);
    
    /**
     * Get's the current health
     * @return Current health
     */
    public int getHealth();
    
    /**
     * Sets the health
     * @param health New health value
     */
    public void setHealth(int health);
    
    /**
     * Gets a list of items in the inventory
     * @return ArrayList of inventory items
     */
    public ArrayList<ItemInterface> getInventory();
    
    /**
     * Sets the contents of the inventory
     * @param inventory ArrayList of items in new inventory
     */
    public void setInventory(ArrayList<ItemInterface> inventory);
    
    /**
     * Drops all the items and the weapon from inventory
     * @return ArrayList of items and weapons dropped
     */
    public ArrayList<ItemInterface> dropAllItems();
    
    /**
     * Used for monsters to attack the Player
     * @return Damage dealt to the player by the monster
     */
    public int attack();
    
}
