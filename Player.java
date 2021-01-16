
import java.util.ArrayList;
import java.util.Random;

public class Player implements PlayerInterface {

    String name;
    int health;
    ArrayList<ItemInterface> inventory;
    Weapon weapon;
    Monster target;
    Room currentRoom;
    
    /**
     * Creates a default player
     */
    public Player() {
    	this("Jim", 100);
    }
    
    /**
     * Creates a custom character with name and health
     * @param name Name of player
     * @param health Health of player
     */
    public Player(String name, int health) {
    	this.name = name;
    	this.health = health;
    	this.weapon = new Weapon(1);
    	this.inventory = new ArrayList<ItemInterface>();
    }
    
    /**
     * Creates a completely custom player
     * @param name Name of player
     * @param health Health of player
     * @param inventory The player's inventory
     * @param weapon The player's weapon
     * @param currentRoom The starting room of the player
     */
    public Player(String name, int health, ArrayList<ItemInterface> inventory, Weapon weapon, Room currentRoom) {
        this.name = name;
        this.health = health;
        this.inventory = inventory;
        this.weapon = weapon;
        this.currentRoom = currentRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ArrayList<ItemInterface> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<ItemInterface> inventory) {
        this.inventory = inventory;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Monster getTarget() {
        return target;
    }

    public void setTarget(Monster target) {
        this.target = target;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }


    public String pickupItem(ItemInterface item) {

        this.inventory.add(item);

        return "Player picks up " + item.getName();
    }

    public String useItem(ItemInterface item) {

        inventory.remove(item);

        return "Player uses " + item.getName();
    }

    public String dropItem(ItemInterface item) {

        inventory.remove(item);

        return "Player drops " + item.getName();
    }

    public int attack(Monster target) {

    	Random random = new Random();

		int damage = random.nextInt(weapon.getHighDamage());
        target.setHealth(target.getHealth() - damage);
        
        return damage;
    }

    /**
     * This method is only used by Monsters and should not be called by a Player
     */
	@Override
	public int attack() {
		//This method is only used by Monsters and should not be called by a player
		return 0;
	}

	/**
	 * This method is only used by Monsters and should not be called by a Player
	 */
	@Override
	public ArrayList<ItemInterface> dropAllItems() {
		//This method is only used by Monsters and should not be called by a player
		return null;
	}

	

}