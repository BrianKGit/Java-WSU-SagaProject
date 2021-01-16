
public interface PlayerInterface extends MonsterInterface{
    
	/**
	 * Picks up items in current room
	 * @param item Item to add to inventory
	 * @return Message related to item picked up
	 */
    public String pickupItem(ItemInterface item);
    
    /**
     * Uses items in inventory
     * @param item Item to be used
     * @return Message related to item used
     */
    public String useItem(ItemInterface item);
    
    /**
     * Attack a target monster
     * @param target Monster to attack
     * @return Message related to attacked target
     */
    public int attack(Monster target);
    
}


