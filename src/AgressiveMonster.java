/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class AgressiveMonster extends Unit{

	/*
	 * Type
	 * 0 - Zombie
	 * 1 - Bandit
	 * 2 - Skeleton
	 * 3 - Necromancer
	 */
	
	private final double SPEED = 0.25;
	private final int MOVE_TO_PLAYER_DISTANCE = 150; 
	private static final String PATH = "assets/units/";
	
	private int type;
	private static final String[] MONSTER_PATH = {"zombie.png","bandit.png","skeleton.png","necromancer.png"};
	private static final int[][] ATTRIBUTES_MONSTER = {{60,10,2000},{40,8,2000},{100,16,2000},{140,30,2000}};
	private int attackDamage,cooldown_timer;
	
	/**
	 * Creates a new AgressiveMonster
	 */
	public AgressiveMonster(float type, float xStart, float yStart) throws SlickException {
		
		super(ATTRIBUTES_MONSTER[(int)type][0],ATTRIBUTES_MONSTER[(int)type][1],ATTRIBUTES_MONSTER[(int)type][2],
				xStart,yStart,true,PATH+MONSTER_PATH[(int)type]);
		
		this.type = (int)type;
		cooldown_timer = 0;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void update(double dir_x, double dir_y, int delta, int block_l, int block_u, int block_r, int block_d)
			throws SlickException {
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void render(Graphics g, Camera camera) throws SlickException {
		if(status){
			entity.drawCentered(x_map - camera.getxPos(), y_map - camera.getyPos());
			float percentage = (hp*100)/maxHp;
			if (type == 0)
				healthRender(g,camera,"zombie",percentage);
			if (type == 1)
				healthRender(g,camera,"bandit",percentage);
			if (type == 2)
				healthRender(g,camera,"skeleton",percentage);
			if (type == 3)
				healthRender(g,camera,"Draelic",percentage); }
	}
	
	public void hasCollide(Player player, int delta) {
		
			double distX = player.getX_map() - x_map, distY = player.getY_map() - y_map;
			double distance = sqrt(pow(distX, 2) + pow(distY, 2));
			
			if (distance > COLLISION_DISTANCE && distance <= MOVE_TO_PLAYER_DISTANCE) {
				double move_x = distX/distance;
				double move_y = distY/distance;
				moveHorizontally(move_x,delta);
				moveVertically(move_y,delta);	
			}
			
			if(distance <= COLLISION_DISTANCE){
				if(cooldown_timer <= 0){
					
					attackDamage = rand.nextInt(damage+1);	
					player.setHp(player.getHp() - attackDamage);
					
					if(player.getHp() <= 0){
						player.setHp(0);
						player.setStatus(false);}
					
					cooldown_timer = cooldown;}
				
				if(cooldown_timer > 0){
					cooldown_timer -= delta;}
			
				}
	}
	
	public void moveHorizontally(double amount, int delta){
		x_map += (float)amount*SPEED*delta;
	}
	
	public void moveVertically(double amount, int delta){
		y_map += (float)amount*SPEED*delta;
	}
}
