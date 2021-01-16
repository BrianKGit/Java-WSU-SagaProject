
import java.util.ArrayList;
import java.util.Random;

public class Monster {

	String name;
	int health;
	int maxHealth;
	ArrayList<Item> inventory;
	Weapon weapon;
	Room currentRoom;
	
	
	/**
	 * Creates a blank monster
	 */
	public Monster() {

	}

	/**
	 * Creates a monster from preset values
	 * @param preset Indicates which monster to make. 1 = Imp | 2 = Goblin | 3 = Skeleton | 4 = Wizard | 5 = Goblin Tinker
	 */
	public Monster(int preset) {
		inventory = new ArrayList<Item>();
		if (preset == 1) {
			this.name = "an Imp";
			this.maxHealth = 20;
			this.health = maxHealth;
			this.weapon = new Weapon(0); // 0 preset = spear
			this.inventory.add(new Item(1)); // Imps carry keys!
		}
		if (preset == 2) {
			this.name = "a Goblin";
			this.maxHealth = 25;
			this.health = maxHealth;
			this.weapon = new Weapon(1); // 1 preset = sword;
			this.inventory.add(new Item(2)); // Goblins carry Potions!
		}
		if (preset == 3) {
			this.name = "a Skeleton";
			this.maxHealth = 25;
			this.health = maxHealth;
			this.weapon = new Weapon(2); // 2 preset = halibard
		}
		
		
		if (preset == 4) {
			this.name = "an Evil Wizard";
			this.maxHealth = 75;
			this.health = maxHealth;
			this.weapon = new Weapon(3);
		}
		if (preset == 5) {
			this.name = "a Goblin Tinkerer";
			this.maxHealth = 25;
			this.health = maxHealth;
			this.weapon = new Weapon(4);
			
		}

	}

	/**
	 * Creates a custom monster
	 * @param name Monster name
	 * @param health Monster's max health
	 * @param inventory Inventory of items
	 * @param weapon Equipped weapon
	 */
	public Monster(String name, int health, ArrayList<Item> inventory, Weapon weapon) {
		this.name = name;
		this.maxHealth = health;
		this.health = this.maxHealth;
		this.inventory = inventory;
		this.weapon = weapon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public ArrayList<ItemInterface> dropAllItems(){
		ArrayList<ItemInterface> retArr = new ArrayList<ItemInterface>();
		retArr.addAll(inventory);
		retArr.add(this.weapon);
		return retArr;
	}

	public int attack() {

		Random random = new Random();

		int damage = random.nextInt(weapon.getHighDamage());

		return damage;
	}

}
