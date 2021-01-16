
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MiniMap extends JPanel {

	private int mapWidth;
	private int mapHeight;
	private Room rootRoom;
	private Room currentRoom;
	private Room[][] miniMap;
	private int[][] mapCreator;
	private int[][] monsterFiller;
	private int locationX;
	private int locationY;

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;

	JPanel[][] panelHolder;
	
	/**
	 * Creates a MiniMap object with default 5x5 dimensions
	 * 
	 */
	public MiniMap() {
		this(5, 5);
	}
	
	/**
	 * Creates a MiniMap with custom size
	 * @param height sets MiniMap height dimension
	 * @param width sets MiniMap width dimension
	 */
	public MiniMap(int height, int width) {
		this.mapWidth = width;
		this.mapHeight = height;
		panelHolder = new JPanel[mapWidth][mapHeight];
		miniMap = new Room[mapWidth][mapHeight];
		setLayout(new GridLayout(mapWidth, mapHeight));

		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < mapWidth; j++) {
				panelHolder[j][i] = new JPanel();
				add(panelHolder[j][i]);
			}
		}
		createMap();
		initMiniMap();
	}
	
	/**
	 * Initializes the MiniMap display
	 */
	private void initMiniMap() {
		currentRoom = rootRoom;
		panelHolder[locationX][locationY].add(new JButton("  *  "));
		updateNorth();
		updateEast();
		updateSouth();
		updateWest();

	}
	/*
	 * move(direction) remove button for current room [ * ] and replace with empty [
	 * ] for any given direction if direction is valid if room isnt locked move to
	 * new room update minimap for new rooms in every direction but the one you just
	 * came from set moved boolean to true remove button for new current room [ ]
	 * and replace with [ * ] to indicate the current room return moved boolean to
	 * caller saying if move was successful or not
	 */
	
	/**
	 * Moves the player throughout the map.
	 * @param direction indicates which direction the player is moving. 0 = north | 1 = east | 2 = south | 3 = west
	 * @param hasKey checks to see if the player has a key and can go through locked doors
	 * @return if the movement was successful return true, otherwise return false
	 */
	public boolean move(int direction, boolean hasKey) { // 0 = north | 1 = east | 2 = south | 3 = west
		boolean moved = false;
		// remove old button and add new blank button
		panelHolder[locationX][locationY].remove(0);
		panelHolder[locationX][locationY].add(new JButton("     "));
		if (direction == NORTH) {
			if (currentRoom.getNorth() != null) {
				if (!currentRoom.getNorth().isLocked() || hasKey) {
					currentRoom = currentRoom.getNorth();
					// change location in room array
					locationY -= 1;
					// update minimap
					updateNorth();
					updateEast();
					updateWest();
					moved = true;
				}
			}
		}
		if (direction == EAST) {
			if (currentRoom.getEast() != null) {
				if (!currentRoom.getEast().isLocked()) {
					currentRoom = currentRoom.getEast();
					locationX += 1;
					updateNorth();
					updateEast();
					updateSouth();
					moved = true;
				}
			}
		}
		if (direction == SOUTH) {
			if (currentRoom.getSouth() != null) {
				if (!currentRoom.getSouth().isLocked()) {
					currentRoom = currentRoom.getSouth();
					locationY += 1;
					updateEast();
					updateSouth();
					updateWest();
					moved = true;
				}
			}
		}
		if (direction == WEST) {
			if (currentRoom.getWest() != null) {
				if (!currentRoom.getWest().isLocked()) {
					currentRoom = currentRoom.getWest();
					locationX -= 1;
					updateNorth();
					updateSouth();
					updateWest();
					moved = true;
				}
			}
		}
		// replace new current room with [ * ] button
		panelHolder[locationX][locationY].remove(0);
		panelHolder[locationX][locationY].add(new JButton("  *  "));

		return moved;
	}

	// updateNorth,East,South,West behave the same
	/*
	 * surround in try catch to catch it tries to update area out of game bounds if
	 * currentRoom.get[direction] has a room in that direction if the minimap
	 * already has a button there remove it if the room is not locked set minimap
	 * with button [ ] else set minimap with button [ [ ] ]
	 * 
	 * 
	 */
	
	/**
	 * Updates the displayed minimap with the room to the north
	 */
	private void updateNorth() {
		try {
			if (currentRoom.getNorth() != null) {
				if (panelHolder[locationX][locationY - 1].getComponentCount() == 1) {
					panelHolder[locationX][locationY - 1].removeAll();
				}
				if (!currentRoom.getNorth().isLocked()) {
					panelHolder[locationX][locationY - 1].add(new JButton("     "));
				} else {
					panelHolder[locationX][locationY - 1].add(new JButton(" [ ] "));
				}

			}
		} catch (ArrayIndexOutOfBoundsException e) {
                    
		}

	}
	
	/**
	 * Updates the displayed minimap with the room to the east 
	 */
	private void updateEast() {
		try {
			if (currentRoom.getEast() != null) {
				if (panelHolder[locationX + 1][locationY].getComponentCount() == 1) {
					panelHolder[locationX + 1][locationY].removeAll();
				}
				if (!currentRoom.getEast().isLocked()) {
					panelHolder[locationX + 1][locationY].add(new JButton("     "));
				} else {
					panelHolder[locationX + 1][locationY].add(new JButton(" [ ] "));
				}

			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
	/**
	 * Updates the displayed minimap with the room to the south
	 */
	private void updateSouth() {
		try {
			if (currentRoom.getSouth() != null) {
				if (panelHolder[locationX][locationY + 1].getComponentCount() == 1) {
					panelHolder[locationX][locationY + 1].removeAll();
				}
				if (!currentRoom.getSouth().isLocked()) {
					panelHolder[locationX][locationY + 1].add(new JButton("     "));
				} else {
					panelHolder[locationX][locationY + 1].add(new JButton(" [ ] "));
				}

			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
	/**
	 * Updates the displayed minimap with the room to the west
	 */
	private void updateWest() {
		try {
			if (currentRoom.getWest() != null) {
				if (panelHolder[locationX - 1][locationY].getComponentCount() == 1) {
					panelHolder[locationX - 1][locationY].removeAll();
				}
				if (!currentRoom.getWest().isLocked()) {
					panelHolder[locationX - 1][locationY].add(new JButton("     "));
				} else {
					panelHolder[locationX - 1][locationY].add(new JButton(" [ ] "));
				}

			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
	
	/**
	 * Creates the map from the hardset values
	 */
	private void createMap() {
		miniMap = new Room[5][5];
		mapCreator = new int[][] { // 0 = blank
				{ 2, 0, 2, 0, 2 }, // 1 = root room, only one of these
				{ 2, 0, 3, 0, 2 }, // 2 = unlocked room
				{ 2, 2, 1, 2, 2 }, // 3 = locked room
				{ 0, 0, 2, 0, 0 }, 
                                { 2, 2, 2, 2, 2 } };
		// Array for filling rooms with monsters
		monsterFiller = new int[][] { // 0 = empty
				{ 5, 0, 4, 0, 2 }, // 1 = Imp
				{ 0, 0, 0, 0, 0 }, // 2 = Goblin
				{ 0, 3, 0, 3, 0 }, // 3 = Skeleton
				{ 0, 0, 3, 0, 0 }, // 4 = Evil Wizard
				{ 2, 0, 0, 0, 1 } }; // 5 = Goblin Tinker

		// Create array of unlinked rooms
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (mapCreator[j][i] == 1) {
					miniMap[j][i] = new Room(false);
					rootRoom = miniMap[j][i];
					locationX = j;
					locationY = i;
				}
				if (mapCreator[j][i] == 2) {
					miniMap[j][i] = new Room(false);
					if (monsterFiller[j][i] > 0) {
						miniMap[j][i].addOccupant(new Monster(monsterFiller[j][i])); // Adds a new monster of preset 1,2
																						// or 3
					}
				}
				if (mapCreator[j][i] == 3) {
					miniMap[j][i] = new Room(true);
					if (monsterFiller[j][i] > 0) {
						miniMap[j][i].addOccupant(new Monster(monsterFiller[j][i])); // Adds a new monster of preset 1,2
																						// or 3
					}

				}
			}
		}

		// Go through room array and link them
		// Only need to call setSouth and setEast because these automatically link both
		// north/south rooms and east/west rooms
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (mapCreator[j][i] != 0) {
					try {
						if (mapCreator[j + 1][i] != 0) { // If there is a room to the south
							miniMap[j][i].setSouth(miniMap[j + 1][i]); // Connect it to the south room
						} // This also links the south room to this current room
					} catch (ArrayIndexOutOfBoundsException e) {
						// Do nothing if we stray out of bounds
					}
					try {
						if (mapCreator[j][i + 1] != 0) { // If there is a room to the east
							miniMap[j][i].setEast(miniMap[j][i + 1]); // Connect it to the east room
						} // This also links the east room to this current room
					} catch (ArrayIndexOutOfBoundsException e) {
						// Do nothing if we stray out of bounds
					}
				}
			}
		}

	}
	
	/**
	 * Gets the current room that the player is in.
	 * @return Current room
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

}
