/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import static java.lang.Math.*;

public class Player extends Unit {

	private static final int START_HP = 100, START_DAMAGE = 26, START_COOLDOWN = 600;
	private static final String PATH_PLAYER = "assets/units/player.png", PATH_PANEL = "assets/panel.png";
	private static final float X_START = 756, Y_START = 684, SPEED = 0.25f;

	private float temp_x = X_START, temp_y = Y_START;
	private int block_posL, block_posU,valueAttack;
	private Image panel;
	private Item[] playerItems;

	/**
	 * Creates a new player
	 */
	public Player() throws SlickException {
		
		super(START_HP, START_DAMAGE, START_COOLDOWN,X_START,Y_START,true, PATH_PLAYER);
		panel = new Image(PATH_PANEL);
		playerItems = new Item[4];
		
		for (int i = 0; i < playerItems.length; i++) {
			playerItems[i] = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(double dir_x, double dir_y, int delta, int block_x, int block_y, int block_l, int block_r)
			throws SlickException {

		// The blocking tiles to the left and up of the player
		block_posL = (int) (x_map / 72);
		block_posU = (int) (y_map / 72);

		// Temporary position of the player
		temp_x += (float) (dir_x * SPEED * delta);
		temp_y += (float) (dir_y * SPEED * delta);

		moveHorizontally(dir_x, block_x);
		moveVertically(dir_y,block_y);
	}
	
	private void moveHorizontally(double dir_x, int block_x){
		if (dir_x == -1){
			if (block_x == 0 || (block_x == 1 && temp_x >= (float) (block_posL * 72) + ENTITY_HALF_SIZE))
				x_map = temp_x;
			else
				this.temp_x = x_map;}

		if (dir_x == 1){
			if (block_x == 0 || (block_x == 1 && temp_x <= (float) ((block_posL + 1) * 72) - ENTITY_HALF_SIZE))
				x_map = temp_x;
			else
				this.temp_x = x_map;}
	}
	
	private void moveVertically(double dir_y, int block_y){
		if (dir_y == -1)
			if (block_y == 0 || (block_y == 1 && temp_y >= (float) (block_posU * 72) + ENTITY_HALF_SIZE))
				y_map = temp_y;
			else
				this.temp_y = y_map;

		if (dir_y == 1)
			if (block_y == 0 || (block_y == 1 && temp_y <= (float) ((block_posU + 1) * 72) - ENTITY_HALF_SIZE))
				y_map = temp_y;
			else
				this.temp_y = y_map;
	}

	/**
	 * {@inheritDoc}
	 */
	public void render(Graphics g, Camera camera) throws SlickException {

		entity.drawCentered(x_map - camera.getxPos(), y_map - camera.getyPos());

	}

	/**
	 * Checks if the player has collide with an object
	 * @param array can be of any class Item, PassiveMonster or AgressiveMonster
	 */
	public void hasCollide(Object[] array) {
		valueAttack = rand.nextInt(maxHp+1);
		if(array instanceof Item[]){
			Item[] items = (Item[])array;
			hasCollideItem(items);
		}
		if(array instanceof AgressiveMonster[]){
			AgressiveMonster[] monsters = (AgressiveMonster[])array;
			hasCollideMonster(monsters,valueAttack);
		}
		if(array instanceof PassiveMonster[]){
			PassiveMonster[] monsters = (PassiveMonster[])array;
			hasCollideMonster(monsters,valueAttack);
		}
		
	}
	
	/**
	 * Checks if player has collided with an item and adds item to inventory
	 */
	private void hasCollideItem(Item[] itemArray){
		for (Item item : itemArray) {
			double distance = sqrt(pow(x_map - item.getX_map(), 2) + pow(y_map - item.getY_map(), 2));
			if (distance <= COLLISION_DISTANCE) {
				item.setStatus(false);
				playerItems[item.getType()] = item;
			}
		}
	}
	
	/**
	 * Checks if player has collided with an AgressiveMonster and attacks the monster
	 * @param valueAttack is the damage that the player causes
	 */
	private void hasCollideMonster(AgressiveMonster[] monsters, int valueAttack){
		for (AgressiveMonster monster : monsters) {
			double distance = sqrt(pow(x_map - monster.getX_map(), 2) + pow(y_map - monster.getY_map(), 2));
			if (distance <= COLLISION_DISTANCE) {
					monster.setHp(monster.getHp() - valueAttack);
					checkMonsterStatus(monster);
			}
		}
	}
	
	/**
	 * Checks if the player has collided with an PassiveMonster and attacks the mosnter
	 * @param valueAttack is the damage that the player causes
	 */
	private void hasCollideMonster(PassiveMonster[] monsters, int valueAttack){
		for (PassiveMonster monster : monsters) {
			double distance = sqrt(pow(x_map - monster.getX_map(), 2) + pow(y_map - monster.getY_map(), 2));
			if (distance <= COLLISION_DISTANCE) {
				monster.setHp(monster.getHp() - valueAttack);
				monster.setAttacked(true);
				checkMonsterStatus(monster);
			}
		}
	}
	
	/**
	 * Checks if the Player has killed the monster
	 */
	private void checkMonsterStatus(AgressiveMonster monster){
		if(monster.getHp() <= 0)
			monster.setStatus(false);
	}

	/**
	 * Checks if the Player has killed the monster
	 */
	private void checkMonsterStatus(PassiveMonster monster){
		if(monster.getHp() <= 0)
			monster.setStatus(false);
	}
	
	/**
	 * Checks if the player has used the item or not 
	 */
	public void useItems(Player player) {
		for (Item item : player.playerItems) {
			
			if (item != null && (!item.isUsed())) {
				if (item.getType() == 0)
					item.AmuletOfVitality(player);
				
				if (item.getType() == 1)
					item.SwordOfStrenght(player);
				
				if (item.getType() == 2)
					item.TomeOfAgility(player);
				
				item.setUsed(true); }
		}
	}
	
	/*****************************************Getters/Setters**********************************************/
	public int getSTART_HP() {
		return START_HP;
	}

	public Item[] getPlayerItems() {
		return playerItems;
	}

	public int getSTART_DAMAGE() {
		return START_DAMAGE;
	}

	public int getSTART_COOLDOWN() {
		return START_COOLDOWN;
	}

	public float getX_START() {
		return X_START;
	}

	public float getY_START() {
		return Y_START;
	}
	
	public void setTemp_y(float temp_y) {
		this.temp_y = temp_y;
	}
	
	public void setTemp_x(float temp_x) {
		this.temp_x = temp_x;
	}
	/***************************************************************************************************/

	/*************************************** Render Panel***********************************************/
	public void renderPanel(Graphics g) {
		// Panel colours
		Color LABEL = new Color(0.9f, 0.9f, 0.4f); // Gold
		Color VALUE = new Color(1.0f, 1.0f, 1.0f); // White
		Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f); // Black, transp
		Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f); // Red, transp

		// Variables for layout
		String text; // Text to display
		int text_x, text_y; // Coordinates to draw text
		int bar_x, bar_y; // Coordinates to draw rectangles
		int bar_width, bar_height; // Size of rectangle to draw
		int hp_bar_width; // Size of red (HP) rectangle
		int inv_x, inv_y; // Coordinates to draw inventory item

		float health_percent; // Player's health, as a percentage

		// Panel background image
		panel.draw(0, RPG.SCREENHEIGHT - RPG.PANELHEIGHT);

		// Display the player's health
		text_x = 15;
		text_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 25;
		g.setColor(LABEL);
		g.drawString("Health:", text_x, text_y);
		text = hp + "/" + maxHp; // TODO: HP / Max-HP

		bar_x = 90;
		bar_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 20;
		bar_width = 90;
		bar_height = 30;
		health_percent = hp/(float)maxHp; // TODO: HP / Max-HP
		hp_bar_width = (int) (bar_width * health_percent);
		text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
		g.setColor(BAR_BG);
		g.fillRect(bar_x, bar_y, bar_width, bar_height);
		g.setColor(BAR);
		g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
		g.setColor(VALUE);
		g.drawString(text, text_x, text_y);

		// Display the player's damage and cooldown
		text_x = 200;
		g.setColor(LABEL);
		g.drawString("Damage:", text_x, text_y);
		text_x += 80;
		text = Integer.toString(damage); // TODO: Damage
		g.setColor(VALUE);
		g.drawString(text, text_x, text_y);
		text_x += 40;
		g.setColor(LABEL);
		g.drawString("Rate:", text_x, text_y);
		text_x += 55;
		text = Integer.toString(cooldown); // TODO: Cooldown
		g.setColor(VALUE);
		g.drawString(text, text_x, text_y);

		// Display the player's inventory
		g.setColor(LABEL);
		g.drawString("Items:", 420, text_y);
		bar_x = 490;
		bar_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 10;
		bar_width = 288;
		bar_height = bar_height + 20;
		g.setColor(BAR_BG);
		g.fillRect(bar_x, bar_y, bar_width, bar_height);

		inv_x = 520;
		inv_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + (RPG.PANELHEIGHT / 2);

		for (Item item : playerItems) {
			// Render the item to (inv_x, inv_y)
			if (item != null) {
				item.renderInPanel(g, inv_x, inv_y);
			}

			inv_x += 72;
		}
	}

	/*************************************** Render Panel***********************************************/

}
