
public class Item implements ItemInterface{
    
    String name;
    String description;
    int weight;
    
    /**
     * Creates items using preset values
     * @param preset Indicates with item to create. 1 = key | 2 = healing potion
     */
    public Item(int preset) {
    	if(preset == 1) {
    		this.name = "a key";
    		this.description = "Use it on a locked door- don't worry, it won't break.";
    		this.weight = 1;
    	}
    	if(preset == 2) {
    		this.name = "a healing potion";
    		this.description = "Take a good swig to feel better.";
    		this.weight = 2;
    	}
    }
    
    /**
     * Creates a custom item
     * @param name Item name
     * @param description Item description
     * @param weight Item weight
     */
    public Item(String name, String description, int weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
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
        
    }

    @Override
    public String toString() {
        return  name +"\n"
              + description + "\n" 
              + weight;
    }
    
    
    
}