
public interface ItemInterface {
    
	/**
	 * 
	 * @return String name of item
	 */
    public String getName();
    
    /**
     * Set the name of an item
     * @param name Item's new name
     */
    public void setName(String name);
    
    /**
     * 
     * @return Weight of the Item
     */
    public int getWeight();
    
    /**
     * Set the weight of an item
     * @param weight Item's new weight
     */
    public void setWeight(int weight);
    
    /**
     * 
     * @return Item's description
     */
    public String getDescription();
    
    /**
     * Set the description of the item
     * @param description Item's new description
     */
    public void setDescription(String description);
    
}