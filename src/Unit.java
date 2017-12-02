/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import java.util.Random;

public abstract class Unit extends Entity {
	
	protected int hp, maxHp, damage, cooldown, timeDialogue;
	protected Random rand;
	Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f); //RED
	Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
	Color VALUE = new Color(1.0f, 1.0f, 1.0f);//White
	
	/**
	 * Updates an unit's position
	 * @param dir_x where the unit is moving in the x-plane
	 * @param dir_y where the unit is moving in the y-plane
	 * @param delta frames per second passed since last update
	 * @param block_l left tile
	 * @param block_u upper tile
	 * @param block_r right tile 
	 * @param block_d down tile
	 * @throws SlickException
	 */
	protected abstract void update(double dir_x, double dir_y, int delta, int block_l,
			int block_u, int block_r,int block_d) throws SlickException;
	
	protected Unit(int maxHp, int damage, int cooldown, float x_start, float y_start, boolean status,String path) throws SlickException{
		
		super(x_start,y_start,status,path);
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.damage = damage;
		this.cooldown = cooldown;
		rand = new Random();
	}
	
	/**
	 * Renders the health bar of a unit
	 */
	protected void healthRender(Graphics g,Camera camera,String string, float percentage){
		
		float widthBar = string.length()*10;
		
		g.setColor(BAR_BG);
		g.fillRect(x_map - camera.getxPos() - 30, y_map - camera.getyPos() - 50, widthBar, 20);
		
		g.setColor(BAR);
		g.fillRect(x_map - camera.getxPos() - 30, y_map - camera.getyPos() - 50, (float)((widthBar*percentage)*0.01), 20);
		
		g.setColor(VALUE);
		g.drawString(string,x_map - camera.getxPos() - 25, y_map - camera.getyPos() - 50);	
	}

	/*************************************** Getters/Setters***********************************************/
	public int getHp() {
		return hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getDamage() {
		return damage;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getTimeDialogue() {
		return timeDialogue;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	/******************************************************************************************************/
}
