/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class PassiveMonster extends Unit {

	private static final int START_HP = 40, START_DAMAGE = 0, START_COOLDOWN = 0,DIRECTION_TIMER = 3000, TIME_NOT_ATTACKED = 5000;
	private final double SPEED = 0.2;
	private final int[][] DIRECTION = {{0,1},{0,-1},{-1,0},{1,0},{1,1},{1,-1},{-1,-1},{-1,1},{0,0}};
	private static final String PATH_PASSIVE_MONSTER = "assets/units/dreadbat.png";
	private int value = 6, wanderMapTimer, attackedTimer = 0;
	private boolean attacked = false;
	private float temp_x, temp_y;

	/**
	 * Creates a new passive monster
	 */
	public PassiveMonster(float x_start, float y_start) throws SlickException {
		
		super(START_HP,START_DAMAGE,START_COOLDOWN,x_start,y_start, true,PATH_PASSIVE_MONSTER);
		wanderMapTimer = DIRECTION_TIMER;
		temp_x = x_map;
		temp_y = y_map;
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(double dir_x, double dir_y, int delta, int block_l, int block_u, int block_r, int block_d)
			throws SlickException {
		
				if (wanderMapTimer <= 0){
					value = rand.nextInt(9);
					wanderMapTimer = DIRECTION_TIMER;}
				else
					wanderMapTimer -= delta;
				
				moveHorizontally(DIRECTION[value][0],delta,block_l,block_r);
				moveVertically(DIRECTION[value][1],delta,block_u,block_d);	
	}
	
	/**
	 * Method to run away from the player if attacked
	 */
	public void runAway(Player player, int delta, int block_l, int block_u, int block_r, int block_d){
		if(player.getX_map() < x_map)
			moveHorizontally(1,delta,block_l,block_r);
		else
			moveHorizontally(-1,delta,block_l,block_r);
		
		if(player.getY_map() < y_map)
			moveVertically(1,delta,block_u,block_r);
		else
			moveVertically(-1,delta,block_u,block_r);
	}

	public void moveHorizontally(double dir_x, int delta, int block_l, int block_r){

		temp_x += (float)(dir_x*SPEED*delta);
		int block_posL = (int) (x_map / World.TILE_WIDTH);
		
		if (dir_x == -1)
			if (block_l == 0 || (block_l == 1 && temp_x >= (float) (block_posL * 72) + 25))
				x_map = temp_x;
			else
				temp_x = x_map;

		if (dir_x == 1)
			if (block_r == 0 || (block_r == 1 && temp_x <= (float) ((block_posL + 1) * 72) - 25))
				x_map = temp_x;
			else
				temp_x = x_map;
	}
	
	public void moveVertically(double dir_y, int delta, int block_u, int block_d){
		
		temp_y += (float)(dir_y*SPEED*delta);
		int block_posU = (int) (y_map / World.TILE_HEIGHT);
		
		if (dir_y == -1)
			if (block_u == 0 || (block_u == 1 && temp_y >= (float) (block_posU * World.TILE_WIDTH) + ENTITY_HALF_SIZE) )
				y_map = temp_y;
			else
				temp_y = y_map;

		if (dir_y == 1)
			if (block_d == 0 || (block_d == 1 && temp_y <= (float) ((block_posU + 1) * World.TILE_HEIGHT) - ENTITY_HALF_SIZE) )
				y_map = temp_y;
			else
				temp_y = y_map;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void render(Graphics g, Camera camera) throws SlickException {
		
		if(status){
			float percentage = (hp*100)/maxHp;
			entity.drawCentered(x_map - camera.getxPos(), y_map - camera.getyPos());
			healthRender(g,camera,"Giant Bat",percentage);}
	}
	
	/*************************************** Getters/Setters***********************************************/
	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	
	public int getAttackedTimer() {
		return attackedTimer;
	}

	public void setAttackedTimer(int attackedTimer) {
		this.attackedTimer = attackedTimer;
	}
	
	public static int getTimeNotAttacked() {
		return TIME_NOT_ATTACKED;
	}
	/******************************************************************************************************/

}
