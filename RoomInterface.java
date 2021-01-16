
import java.util.ArrayList;

public interface RoomInterface {
	
	/**
	 * 
	 * @return An ArrayList of items in the room.
	 */
    public ArrayList<ItemInterface> getContents();
    
    /**
     * Sets contents of room. Call with new ArrayList of type ItemInterface to clear the room's contents
     * @param items Contents to be set to the room
     */
    public void setContents(ArrayList<ItemInterface> items);
    
    /**
     * 
     * @return Gets the room to the north of this room
     */
    public Room getNorth();
    
    /**
     * 
     * @return Gets the room to the east of this room
     */
    public Room getEast();
    
    /**
     * 
     * @return Gets the room to the south of this room
     */
    public Room getSouth();
    
    /**
     * 
     * @return Gets the room to the west of this room
     */
    public Room getWest();
    
    /**
     * Links rooms together via North-South connections
     * @param r Room to link to the north
     */
    public void setNorth(Room r);
    
    /**
     * Links rooms together via East-West connections
     * @param r Room to link to the east
     */
    public void setEast(Room r);
    
    /**
     * Links rooms together via North-South connections
     * @param r Room to link to the south
     */
    public void setSouth(Room r);
    
    /**
     * Links rooms together via East-West connections
     * @param r Room to link to the south
     */
    public void setWest(Room r);
    
    /**
     * 
     * @return Returns whether the room is locked or not.
     */
    public boolean isLocked();
    
    /**
     * Occupants of the room
     * @return ArrayList of monsters in room
     */
    public ArrayList<Monster> getOccupants();
    

    /**
     * Adds monsters the room
     * @param occupant Monster to be added to the room
     */
    public void addOccupant(Monster occupant);
    
}
