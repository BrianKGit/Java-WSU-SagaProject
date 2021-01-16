
public class Weapon implements WeaponInterface{
    
    String name;
    String description;
    int weight;
    int highDamage;
    String weaponSound; 

    /**
     * Creates a blank weapon
     */
    public Weapon() {
    	
    }
    
    /**
     * Creates a weapon with preset values
     * @param preset Indicates which weapon to create. 0 = Spear | 1 = Sword | 2 = Halibard | 3 = Magic staff | 4 = Shotgun
     */
    public Weapon(int preset) {
    	if(preset == 0) {
    		this.name = "Spear";
    		this.description = "A spear that's useful for poking things.";
    		this.weight = 5;
    		this.highDamage = 8;
    		this.weaponSound = "jab";
    	}
    	if(preset == 1) {
    		this.name = "Sword";
    		this.description = "A staple of any adventurer.";
    		this.weight = 8;
    		this.highDamage = 10;
    		this.weaponSound = "slash";
    	}
    	if(preset == 2) {
    		this.name = "Halibard";
    		this.description = "A threatening looking halibard.";
    		this.weight = 10;
    		this.highDamage = 12;
    		this.weaponSound = "hack";
    	}
    	if(preset == 3) {
    		this.name = "Magic staff";
    		this.description = "A staff crackling with evil energy.";
    		this.weight = 5;
    		this.highDamage = 15;
    		this.weaponSound = "zap";
    	}
    	if(preset == 4) {
    		this.name = "Shotgun";
    		this.description = "A boomstick";
    		this.weight = 5;
    		this.highDamage = 20;
    		this.weaponSound = "BOOM";
    	}
    }

    /**
     * Creates a custom weapon
     * @param name Weapon name
     * @param description Weapon description
     * @param weight Weight of the weapon
     * @param highDamage Highest damage value for weapon
     */
    public Weapon(String name, String description, int weight, int highDamage) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.highDamage = highDamage;
        this.weaponSound = "whack";
    }
    
    //setters and getters
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name; 
        
    }
    
    public int getWeight(){
        return weight;
    }
    
    public void setWeight(int weight){
        this.weight = weight; 
        
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description; 
        
    }
    
    public int getHighDamage(){
        
        return highDamage;
    }
    
    public String getWeaponSound(){
        return weaponSound; 
    }
    
    
    @Override
    public void setHighDamage(int highDamage){
        this.highDamage = highDamage;
    }
    
    //Weapon toString

    @Override
    public String toString() {
        return   name + "\n" 
              + description + "\n"
              + weight + " pounds\n"
              + highDamage + " high damage\n";
    }
    
    
}