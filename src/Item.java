/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Item extends Entity{
	
	/*
	 * Type
	 * 0 - Amulet of Vitality
	 * 1 - Sword of Strength
	 * 2 - Tome of Agility
	 * 3 - Elixir of Life
	 */
	
	private final int MAX_HP_INCREASE = 80, DAMAGE_INCREASE = 30, COOLDOWN_DECREASE = 300;
	private static final String PATH = "assets/items/";
	private static final String[] ITEM_PATH = {"amulet.png","sword.png", "tome.png", "elixir.png"};
	
	private int type;
	private boolean used;
	
	public void setUsed(boolean used) {
		this.used = used;
	}

	public Item(float type, float xStart, float yStart) throws SlickException {
		
		super(xStart,yStart,true,PATH+ITEM_PATH[(int)type]);
		
		this.type = (int) type;	
		used = false;
		
	}
	
	public boolean isUsed() {
		return used;
	}

	/**
	 * {@inheritDoc}
	 */
	public void render(Graphics g, Camera camera) throws SlickException {
		
		if(status){
			entity.drawCentered(x_map - camera.getxPos(), y_map - camera.getyPos());}
	}
	
	public void renderInPanel(Graphics g,float x, float y){
		entity.drawCentered(x, y);
	}
	
	public void AmuletOfVitality(Player player){
		player.setMaxHp(player.getMaxHp() + MAX_HP_INCREASE);
	}
	
	public void SwordOfStrenght(Player player){
		player.setDamage(player.getDamage() + DAMAGE_INCREASE);
	}
	
	public int getType() {
		return type;
	}

	public void TomeOfAgility(Player player){
		player.setCooldown(player.getCooldown() - COOLDOWN_DECREASE);
	}
	
}
