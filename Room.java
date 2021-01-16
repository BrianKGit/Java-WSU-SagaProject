
import java.util.ArrayList;

public class Room implements RoomInterface {
	private Room northConnection;
	private Room eastConnection;
	private Room southConnection;
	private Room westConnection;
	private boolean isLocked;
	private ArrayList<ItemInterface> contents;
	private ArrayList<Monster> occupants;
	
	/**
	 * Creates a new room
	 * @param isLocked Sets whether the room is locked and needs a key or not.
	 */
	public Room(boolean isLocked) {
		this.isLocked = isLocked;
		contents = new ArrayList<ItemInterface>();
		occupants = new ArrayList<Monster>();
	}

	public void setNorth(Room r) {
		if(northConnection == null && r.getSouth() == null) {
			northConnection = r;
			r.connectSouth(this);
		} else {
			throw new IllegalArgumentException("One or more of these room links already exist.");
		}
	}
	
	/**
	 * Used in setSouth to connect rooms automatically
	 * @param r Room to connect via north direction
	 */
	private void connectNorth(Room r) {
		northConnection = r;
	}

	public void setEast(Room r) {
		if(eastConnection == null && r.getWest() == null) {
			eastConnection = r;
			r.connectWest(this);
		} else {
			throw new IllegalArgumentException("One or more of these room links already exist.");
		}
	}
	
	/**
	 * Used in setWest to connect rooms automatically
	 * @param r Room to connect via east direction
	 */
	private void connectEast(Room r) {
		eastConnection = r;
	}

	public void setSouth(Room r) {
		if(southConnection == null && r.getNorth() == null) {
			southConnection = r;
			r.connectNorth(this);
		} else {
			throw new IllegalArgumentException("One or more of these room links already exist.");
		}
	}
	
	/**
	 * Used in setNorth to connect rooms automatically
	 * @param r Room to connect via south direction
	 */
	private void connectSouth(Room r) {
		southConnection = r;
	}

	public void setWest(Room r) {
		if(westConnection == null && r.getEast() == null) {
			westConnection = r;
			r.connectEast(this);
		} else {
			throw new IllegalArgumentException("One or more of these room links already exist.");
		}
	}
	
	/**
	 * Used in setEast to connect rooms automatically
	 * @param r Room to connect via west direction
	 */
	private void connectWest(Room r) {
		westConnection = r;
	}

	public Room getNorth() {
		return northConnection;
	}

	public Room getEast() {
		return eastConnection;
	}

	public Room getSouth() {
		return southConnection;
	}

	public Room getWest() {
		return westConnection;
	}
	
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isLocked() {
		return isLocked;
	}
	
	public String look() {
		String lookRoom = "You are in a room with ";
		if(occupants.size() == 0) {
			lookRoom += "no enemies";
		}
		for(int i=0; i<occupants.size(); i++) {
			lookRoom += occupants.get(i).getName();
			if(i+1<occupants.size()) {
				lookRoom += ", ";
				if(i+1 == occupants.size()) {
					lookRoom += "and ";
				}
			}
		}
		lookRoom += ".\nOn the floor you see ";
		if(contents.size() == 0) {
			lookRoom += "nothing interesting";
		} else {
			for(int i=0; i<contents.size(); i++) {
				lookRoom += contents.get(i).getName();
				if(i+1<contents.size()) {
					lookRoom += ", ";
					if(i+2 == contents.size()) {
						lookRoom += "and ";
					}
				}
			}
		}
		lookRoom += ".";
		return lookRoom;
	}
	


	@Override
	public ArrayList<ItemInterface> getContents() {
		// TODO Auto-generated method stub
		return contents;
	}

	@Override
	public void setContents(ArrayList<ItemInterface> items) {
		contents = items;
	}
	
	public void addContents(ArrayList<ItemInterface> items) {
		for(int i=0; i<items.size(); i++) {
			this.contents.add(items.get(i));
			
		}
	}
	
	public void addContent(ItemInterface item) {
		this.contents.add(item);
	}

	@Override
	public ArrayList<Monster> getOccupants() {
		return occupants;
	}


	@Override
	public void addOccupant(Monster occupant) {
		occupants.add(occupant);
	}
	

}
