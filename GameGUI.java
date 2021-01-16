
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

public class GameGUI {

    MiniMap miniMap;
    JFrame mainFrame;
    JPanel mainPanel;
    JTextArea mainOutputArea;
    JTextArea inventoryArea;
    Player player;
    static boolean restart;
    
    
    /**
     * Creates the game and handles all the ties between classes.
     */
    public GameGUI() {

        //initialize the restart flag
        restart = false;

        // vvvvvvvvvv Setup Game Screens vvvvvvvvvv
        
        // Create map and player
        MiniMap miniMap = new MiniMap();
        Player player = new Player();

        // Setup Main GUI
        JFrame mainFrame = new JFrame();
        JPanel mainPanel = new JPanel();
        JPanel arrowButtonPanel = new JPanel();
        JPanel actionButtonPanel = new JPanel();
        JPanel deathPanel = new JPanel();
        JPanel inventoryPanel = new JPanel();
        JTextArea inventoryArea = new JTextArea();
        JTextArea mainOutputArea = new JTextArea();

        // Setup Battle GUI
        JPanel battleInventoryPanel = new JPanel();
        JPanel battlePanel = new JPanel();
        JPanel battleButtonPanel = new JPanel();
        JTextArea battleOutputArea = new JTextArea();
        JTextArea battleInventoryArea = new JTextArea();
        JProgressBar battleHealthBar = new JProgressBar();
        JLabel battleHealthLabel =  new JLabel("Your Health");
        JProgressBar enemyHealthBar = new JProgressBar();
        JLabel enemyHealthLabel = new JLabel();
        
        // Setup Death GUI
        JPanel deathButtonPanel = new JPanel();
        JLabel deathLabel = new JLabel("You have died");
        
        // Setup Victory GUI
        JPanel victoryPanel = new JPanel();
        JLabel victoryLabel = new JLabel("You are victorious!");
        JPanel victoryButtonPanel = new JPanel();
        

        // ^^^^^^^^^^ Setup Game Screens ^^^^^^^^^^
        
        // vvvvvvvvvv Main Game Screen vvvvvvvvvv
        
        // Main Inventory Area
        inventoryArea.setEditable(false);
        inventoryArea.setPreferredSize(new Dimension(150, 300));
        inventoryPanel.add(inventoryArea);

        // Main Output Area
        mainOutputArea.setEditable(false);
        JScrollPane scrollOutputArea = new JScrollPane(mainOutputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollOutputArea.setPreferredSize(new Dimension(380, 300));
        
        // Player health bar
        JProgressBar healthBar = new JProgressBar();
        healthBar.setValue(player.getHealth());
        
        // Health Bar Header
        JLabel healthLabel = new JLabel("Your Health");
        
        //Health Panel
        JPanel healthPanel = new JPanel();
        healthPanel.setLayout(new GridLayout(2,1));
        healthPanel.add(healthLabel);
        healthPanel.add(healthBar);
        
        
        // Look Button code
        JButton lookButton = new JButton("Look");
        lookButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Display the contents of the current room to player in mainOutputArea
                mainOutputArea.append("\n" + miniMap.getCurrentRoom().look());
                
            }// end actionPerformed

        });// end Look Button code
        
        // Fight Button code
        JButton fightButton = new JButton("Fight"); //fightButtOff
        fightButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                if (!miniMap.getCurrentRoom().getOccupants().isEmpty()) {
                    
                    battleOutputArea.setText("You enter battle against ");
                    
                    for (int i = 0; i < miniMap.getCurrentRoom().getOccupants().size(); i++) {
                        
                        battleOutputArea.append(miniMap.getCurrentRoom().getOccupants().get(i).getName());
                        
                        if (i + 1 < miniMap.getCurrentRoom().getOccupants().size()) {
                            
                            battleOutputArea.append(", ");
                            
                        }// end if
                        
                        if (i + 2 == miniMap.getCurrentRoom().getOccupants().size()) {
                            battleOutputArea.append("and ");
                            
                        }// end if
                        
                    }// end for loop
                    
                    battleInventoryArea.setText("");
                    for(int i=0; i<player.getInventory().size(); i++) {
                    	battleInventoryArea.append(player.getInventory().get(i).getName() + "\n");
                    }
                    battleOutputArea.append(".");
                    
                    String labelText = miniMap.getCurrentRoom().getOccupants().get(0).getName();
                    labelText = labelText.substring(0, 1).toUpperCase() + labelText.substring(1);
                    enemyHealthLabel.setText(labelText);
                    enemyHealthBar.setValue(100);
                   
                    mainFrame.remove(mainPanel);
                    mainFrame.add(battlePanel);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    
                }// end if
                
            }// end actionPerformed
            
        });// end Fight Button code

        // Heal Button code
        JButton healButton = new JButton("Heal");
        healButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (int i = 0; i < player.getInventory().size(); i++) {
                    
                    // check inventory to see if player has a health potion
                    if (player.getInventory().get(i).getName().equals("a healing potion")) {
                        
                        // restore health to max
                        mainOutputArea.append("\nYou heal from " + player.getHealth());
                        player.setHealth(100);
                        healthBar.setValue(player.getHealth());
                        battleHealthBar.setValue(player.getHealth());
                        mainOutputArea.append(" to " + player.getHealth() + ".");

                        // remove health from inventory
                        player.getInventory().remove(i);
                        
                        // stop the loop
                        String iString = "";
                        
                        for (int j = 0; j < player.getInventory().size(); j++) {
                            
                            iString += player.getInventory().get(j).getName() + "\n";
                            
                        }// end for loop
                        
                        inventoryArea.setText(iString);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        break;
                        
                    } else {
                        
                        // if player does not have a heal potion, display message
                        mainOutputArea.append("\nYou do not have a healing potion to use.");
                        
                    } // end if/else
                    
                } // end for loop
                
            }// end actionPerformed

        });// end Heal Button code

        // Pick Up Item Button
        JButton pickUpButton = new JButton("Take");
        pickUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	boolean droppedWeaponFlag = false;
                // If there is something in the room to take
                if (miniMap.getCurrentRoom().getContents().isEmpty() == false) {

                    // Take everything in the room
                    for (int i = 0; i < miniMap.getCurrentRoom().getContents().size(); i++) {
                        if(miniMap.getCurrentRoom().getContents().get(i) instanceof Item) {
                        	player.pickupItem(miniMap.getCurrentRoom().getContents().get(i));
                        }
                        
                        if(miniMap.getCurrentRoom().getContents().get(i) instanceof Weapon && !droppedWeaponFlag) {
                        	Weapon tempWeapon = (Weapon) miniMap.getCurrentRoom().getContents().get(i);
                        	if(tempWeapon.getHighDamage() <= player.getWeapon().getHighDamage()) {
                        		mainOutputArea.append("\nYour " + player.getWeapon().getName() +
                        				" is better than the weapon on the floor.");
                        	} else {
                        		Weapon droppedWeapon = player.getWeapon();
                        		player.setWeapon(tempWeapon);
                        		mainOutputArea.append("\nYou drop your " + droppedWeapon.getName() + 
                        				" for a " + tempWeapon.getName());
                        		miniMap.getCurrentRoom().addContent(droppedWeapon);
                        		droppedWeaponFlag = true;
                        	}
                        }
                        

                    }// end for loop
                    
                    // Update the inventoryArea display
                    String iString = "";
                    
                    for (int i = 0; i < player.getInventory().size(); i++) {
                        
                        iString += player.getInventory().get(i).getName() + "\n";
                        
                    }//end for loop
                    
                    inventoryArea.setText(iString);

                    // Redraw the frame with updated textArea
                    mainFrame.revalidate();
                    mainFrame.repaint();

                    // Clear the room of all items
                    miniMap.getCurrentRoom().setContents(new ArrayList<ItemInterface>());
                    
                }// end if
                
            }// end actionPerformed
            
        });// end Pick Up Button code

        // Direction Controls
        JPanel arrowPanelUpDown = new JPanel();
        arrowPanelUpDown.setLayout(new GridLayout(2, 1));
        
        // Up Button code
        JButton upButton = new JButton("Up");
        upButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                // Check to see if the player has a key in their inventory
                boolean hasKey = false;
                
                for (int i = 0; i < player.getInventory().size(); i++) {
                    
                    if (player.getInventory().get(i).getName().equals("a key")) {
                        hasKey = true;
                        
                    }// end if
                    
                }// end for loop

                // Attempt to move to the north
                if (miniMap.move(MiniMap.NORTH, hasKey)) {

                    // If you moved into a locked room
                    if (miniMap.getCurrentRoom().isLocked()) {

                        // Change the room to unlocked
                        miniMap.getCurrentRoom().setLocked(false);
                        mainOutputArea.append("\nYou unlock the door with your key.");
                        
                    }// end if

                    // Display the contents of the room to the player in mainOutputArea
                    mainOutputArea.append("\n" + miniMap.getCurrentRoom().look());

                    // Drops the mainOutputArea text scrollbar to the bottom to display newest
                    // output
                    scrollOutputArea.getVerticalScrollBar()
                            .setValue(scrollOutputArea.getVerticalScrollBar().getMaximum());
                    
                }// end if
                
                // redraw the screen
                mainFrame.revalidate();
                mainFrame.repaint();
                
            }// end actionPerformed

        });// end Up Button code

        // Down Button code
        JButton downButton = new JButton("Down");
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Check to see if the player has a key in their inventory
                boolean hasKey = false;
                
                for (int i = 0; i < player.getInventory().size(); i++) {
                    
                    if (player.getInventory().get(i).getName().equals("a key")) {
                        hasKey = true;
                        
                    }// end if
                    
                }// end for loop

                // Attempt to move to the south
                if (miniMap.move(MiniMap.SOUTH, hasKey)) {

                    // If the player enters a locked room
                    if (miniMap.getCurrentRoom().isLocked()) {

                        // Change the room to unlocked
                        miniMap.getCurrentRoom().setLocked(false);
                        mainOutputArea.append("\nYou unlock the door with your key.");
                        
                    }// end if

                    // Display the contents of the current room to the player in mainOutputArea
                    mainOutputArea.append("\n" + miniMap.getCurrentRoom().look());
                    
                }// end if

                // Redraw the screen
                mainFrame.revalidate();
                mainFrame.repaint();
                
            }// end actionPerformed

        });// end Down Button code

        // Right Button code
        JButton rightButton = new JButton("Right");
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Check to see if the player has a key in their inventory
                boolean hasKey = false;
                
                for (int i = 0; i < player.getInventory().size(); i++) {
                    
                    if (player.getInventory().get(i).getName().equals("a key")) {
                        hasKey = true;
                        
                    }// end if
                    
                }// end for loop

                // Attempt to move to the east
                if (miniMap.move(MiniMap.EAST, hasKey)) {

                    // If the player enters a locked room
                    if (miniMap.getCurrentRoom().isLocked()) {

                        // Change the room to unlocked
                        miniMap.getCurrentRoom().setLocked(false);
                        mainOutputArea.append("\nYou unlock the door with your key.");
                        
                    }// end if

                    // Display the contents of the current room to the player in mainOutputArea
                    mainOutputArea.append("\n" + miniMap.getCurrentRoom().look());
                    
                }// end if

                // Redraw the screen
                mainFrame.revalidate();
                mainFrame.repaint();
                
            }// end actionPerformed

        });// end Right Button code

        // Left Button code
        JButton leftButton = new JButton("Left");
        leftButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Check to see if the player has a key in their inventory
                boolean hasKey = false;
                
                for (int i = 0; i < player.getInventory().size(); i++) {
                    
                    if (player.getInventory().get(i).getName().equals("a key")) {
                        hasKey = true;
                        
                    }// end if
                    
                }// end for loop

                // Attempt to the move to the west
                if (miniMap.move(MiniMap.WEST, hasKey)) {

                    // If the player enters a locked room
                    if (miniMap.getCurrentRoom().isLocked()) {

                        // Set the room to unlocked
                        miniMap.getCurrentRoom().setLocked(false);
                        mainOutputArea.append("\nYou unlock the door with your key.");
                        
                    }// end if

                    // Display the contents of the room to the player in the mainOutputArea
                    mainOutputArea.append("\n" + miniMap.getCurrentRoom().look());
                    
                }// end if

                // Redraw the screen
                mainFrame.revalidate();
                mainFrame.repaint();
                
            }// end actionPerformed

        });// end Left Button code
        
        // Add Directional Buttons to Main GUI
        arrowPanelUpDown.add(upButton);
        arrowPanelUpDown.add(downButton);
        arrowButtonPanel.setLayout(new GridLayout(1, 3));
        arrowButtonPanel.add(leftButton);
        arrowButtonPanel.add(arrowPanelUpDown);
        arrowButtonPanel.add(rightButton);

        // Add Action Buttons to Main GUI
        actionButtonPanel.setLayout(new GridLayout(2, 2));
        actionButtonPanel.add(fightButton);
        actionButtonPanel.add(healButton);
        actionButtonPanel.add(lookButton);
        actionButtonPanel.add(pickUpButton);

        // Setup Main GUI Layout
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Inventory
        c.gridwidth = 2;
        c.gridheight = 2;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(inventoryPanel, c);
        
        // Health Panel
        c.gridy = 2;
        c.insets = new Insets(100, 10, 0, 0);
        mainPanel.add(healthPanel, c);
        
        
        
        // Output Area
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 0;
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(scrollOutputArea, c);

        // Mini Map
        c.anchor = GridBagConstraints.NORTHEAST;
        c.gridy = 0;
        c.gridx = 6;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(miniMap, c);
        
        // Arrow Button Panel
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.insets = new Insets(100, 0, 0, 0);
        mainPanel.add(arrowButtonPanel, c);
        
        // Action Button Panel
        c.gridy = 2;
        c.gridx = 5;
        c.insets = new Insets(100, 0, 0, 0);
        mainPanel.add(actionButtonPanel, c);

        // ^^^^^^^^^^ Main Game Screen ^^^^^^^^^^
        
        // vvvvvvvvvv Battle Game Screen vvvvvvvvvv
        
        // Battle Inventory Area
        battleInventoryArea.setEditable(false);
        battleInventoryArea.setPreferredSize(new Dimension(150, 300));
        battleInventoryPanel.add(battleInventoryArea);

        // Battle Output Area
        battleOutputArea.setEditable(false);
        JScrollPane scrollBattleArea = new JScrollPane(battleOutputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBattleArea.setPreferredSize(new Dimension(380, 300));
        
        // Battle Health Panel
        JPanel battleHealthPanel = new JPanel();
        battleHealthBar.setValue(player.getHealth());
        battleHealthPanel.setLayout(new GridLayout(2,1));
        battleHealthPanel.add(battleHealthLabel);
        battleHealthPanel.add(battleHealthBar);
        
        // Enemy Health Bar & Panel
        enemyHealthBar.setValue(100);
        JPanel enemyHealthPanel = new JPanel();
        enemyHealthPanel.setLayout(new GridLayout(2,1));
        enemyHealthPanel.add(enemyHealthLabel);
        enemyHealthPanel.add(enemyHealthBar);
        
        
        // Action Listener for Monster Attack
        ActionListener monsterAttack = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < miniMap.getCurrentRoom().getOccupants().size(); i++) {
                    int damage = miniMap.getCurrentRoom().getOccupants().get(i).attack();
                    player.setHealth(player.getHealth() - damage);
                    healthBar.setValue(player.getHealth());
                    battleHealthBar.setValue(player.getHealth());
                    if (damage > 0) {
                        battleOutputArea.append("\n" + miniMap.getCurrentRoom().getOccupants().get(i).getName()
                                + " deals " + damage + " damage to you with a "
                                + miniMap.getCurrentRoom().getOccupants().get(i).getWeapon().getWeaponSound());
                    } else {
                        
                        battleOutputArea.append("\n" + miniMap.getCurrentRoom().getOccupants().get(i).getName()
                                + " swings it's " + miniMap.getCurrentRoom().getOccupants().get(i).getWeapon().getName()
                                + " and misses.");
                        
                    }// end if/else
                    
                    if (player.getHealth() <= 0) {
                        mainFrame.remove(battlePanel);
                        mainFrame.add(deathPanel);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        
                    }// end if
                    
                }// end for loop
                
            }// end actionPerformed
            
        };// end Monster Attack

        // Attack Button code
        JButton attackButton = new JButton("Attack");
        attackButton.addActionListener(monsterAttack);
        attackButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                int damageDealt = player.attack(miniMap.getCurrentRoom().getOccupants().get(0));
                double enemyHealthPercent = (double) miniMap.getCurrentRoom().getOccupants().get(0).getHealth() 
                		/ (double) miniMap.getCurrentRoom().getOccupants().get(0).getMaxHealth() * 100;
                enemyHealthBar.setValue((int) enemyHealthPercent);
                
                
                if (damageDealt > 0) {
                    
                    battleOutputArea.append("\nYou deal " + damageDealt + " damage to "
                            + miniMap.getCurrentRoom().getOccupants().get(0).getName() + " with a " + 
                    		player.getWeapon().getWeaponSound());
                    
                } else {
                    
                    battleOutputArea.append("\nYou swing your " + player.getWeapon().getName() + " and miss.");
                    
                }// end if/else

                if (miniMap.getCurrentRoom().getOccupants().get(0).getHealth() <= 0) {
                    ArrayList<ItemInterface> droppedItems = miniMap.getCurrentRoom().getOccupants().get(0).dropAllItems();
                    
                    
                    // For each item dropped output a message to the mainOutputArea
                    for (int i = 0; i < droppedItems.size(); i++) {
                        String temp = droppedItems.get(i).getName().substring(0, 1).toUpperCase()
                                + droppedItems.get(i).getName().substring(1);
                        battleOutputArea.append("\n" + temp + " drops to the ground.");

                        // Add these items to the room's contents
                        miniMap.getCurrentRoom().addContent(droppedItems.get(i));
                    }// end for loop

                    // Remove the corpse
                    if(miniMap.getCurrentRoom().getOccupants().get(0).getName().equals("an Evil Wizard")) {
                    	mainFrame.remove(battlePanel);
                        mainFrame.add(victoryPanel);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        return;
                    }
                    miniMap.getCurrentRoom().getOccupants().remove(0);
                    if (miniMap.getCurrentRoom().getOccupants().isEmpty()) {
                        mainFrame.remove(battlePanel);
                        mainFrame.add(mainPanel);
                        mainOutputArea.setText("You leave battle victorious.");
                        mainOutputArea.append("\n" + miniMap.getCurrentRoom().look());
                        inventoryArea.setText("");
                        for(int i=0; i<player.getInventory().size(); i++) {
                        	inventoryArea.append(player.getInventory().get(i).getName() + "\n");
                        }
                        mainFrame.revalidate();
                        mainFrame.repaint();

                    }// end if

                }// end if

            }// end actionPerformed

        });// end Attack Button code

        // Run Button code
        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                mainFrame.remove(battlePanel);
                mainFrame.add(mainPanel);
                mainFrame.revalidate();
                mainFrame.repaint();
                mainOutputArea.setText("You wimp out and run.");
                
            }//end actionPerformed
            
        });// end Run Button code

        // Heal In Battle Button code
        JButton healInBattleButton = new JButton("Heal");
        healInBattleButton.addActionListener(monsterAttack);
        healInBattleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boolean usedPotion = false;
                
                for (int i = 0; i < player.getInventory().size(); i++) {
                    
                    // check inventory to see if player has a health potion
                    if (player.getInventory().get(i).getName().equals("a healing potion")) {
                        
                        // restore health to max
                        battleOutputArea.append("\nYou heal from " + player.getHealth());
                        player.setHealth(100);
                        healthBar.setValue(player.getHealth());
                        battleHealthBar.setValue(player.getHealth());
                        battleOutputArea.append(" to " + player.getHealth() + ".");
                        usedPotion = true;
                        
                        // remove health from inventory
                        player.getInventory().remove(i);
                        
                        // stop the loop
                        String iString = "";
                        for (int j = 0; j < player.getInventory().size(); j++) {
                            
                            iString += player.getInventory().get(j).getName() + "\n";
                            
                        }// end for loop
                        
                        battleInventoryArea.setText(iString);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        break;
                        
                    }// end if
                    
                }// end for loop
                
                if (!usedPotion) {
                    
                    battleOutputArea.append("\nYou have no potion to use and\nwaste time looking in your pack.");
                    
                }// end if
                
            }// end actionPerformed
            
        });
        // end Heal In Battle Button code

        // Battle Buttons
        battleButtonPanel.setLayout(new GridLayout(1, 3));
        battleButtonPanel.add(attackButton);
        battleButtonPanel.add(healInBattleButton);
        battleButtonPanel.add(runButton);
        
        // Setup Battle GUI Layout
        battlePanel.setLayout(new GridBagLayout());
        
        // Battle Inventory
        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 2;
        c.gridheight = 2;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 0;
        battlePanel.add(battleInventoryPanel, c);
        
        // Battle Health Panel
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 3;
        battlePanel.add(battleHealthPanel, c);
        
        // Battle Output Area
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.gridy = 0;
        battlePanel.add(scrollBattleArea, c);
        
        // Enemy Health Panel
        c.gridx = 2;
        c.gridy = 3;
        c.insets = new Insets (0, 200, 0, 0);
        battlePanel.add(enemyHealthPanel, c);
        
        // Battle Button Panel
        c.gridy = 4;
        c.gridx = 3;
        c.insets = new Insets(100, 0, 0, 0);
        battlePanel.add(battleButtonPanel, c);

        // ^^^^^^^^^^ Battle Game Screen ^^^^^^^^^^
        
        // vvvvvvvvvv Death Game Screen vvvvvvvvvv
        
        // Reset Button code
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	mainFrame.setVisible(false);
            	restart = true;
            	
                GameGUI.restart(restart);
                
                
            }// end actionPerformed
            
        });// end Reset Button code

        // Exit Button code
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
                
            }// end actionPerformed
            
        });//end Exit Button code

        //Death Panel Buttons
        deathButtonPanel.setLayout(new GridLayout(1, 2));
        deathButtonPanel.add(resetButton);
        deathButtonPanel.add(exitButton);
        
        
        // Setup Death GUI Layout
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 3;
        c.gridy = 2;
        deathPanel.setLayout(new GridBagLayout());
        deathPanel.add(deathLabel);
        
        // Death Button Panel
        c.insets = new Insets(100, 100, 100, 100);
        c.gridx = 3;
        c.gridy = 3;
        deathPanel.add(deathButtonPanel, c);

        // ^^^^^^^^^^ Death Game Screen ^^^^^^^^^^
        
        // vvvvvvvvvv   Victory Screen  vvvvvvvvvv
        
        JButton victoryResetButton = new JButton("Play Again");
        victoryResetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				restart = true;
				GameGUI.restart(restart);
			}
        	
        });
        JButton victoryQuitButton = new JButton("Quit");
        victoryQuitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
				
			}
        	
        });
        victoryPanel.setLayout(new GridBagLayout());
        victoryButtonPanel.setLayout(new GridLayout(1, 2));
        victoryButtonPanel.add(victoryResetButton);
        victoryButtonPanel.add(victoryQuitButton);
        
        // Victory Label
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 3;
        c.gridy = 2;
        victoryPanel.add(victoryLabel, c);
        
        // Victory Button Panel
        c.insets = new Insets(100, 100, 100, 100);
        c.gridx = 3;
        c.gridy = 3;
        victoryPanel.add(victoryButtonPanel, c);
        
        
        
        // ^^^^^^^^^^   Victory Screen  ^^^^^^^^^^

        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.pack();

    }// end GameGUI

    public static void main(String[] args) {
        
        // Start Game
        new GameGUI();

    }// end main
    
    /**
     * Resets the game when a game over state is reached and the player chooses to play again
     * @param trigger If the flag is true, then we are restarting the game, otherwise this does nothing.
     */
    public static void restart(boolean trigger) {
        
        if (trigger == true) {
            
            // Restart Game
            new GameGUI();
            
        }// end if
        
    }// end restart
    
}// end class
