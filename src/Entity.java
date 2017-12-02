/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

public abstract class Entity {
	
	protected final int ENTITY_HALF_SIZE = 25;
 	protected float x_map, y_map;
	protected final double COLLISION_DISTANCE = 50;
	protected boolean status;
	protected Image entity;
	
	/**
	 * Renders an entity onto the screen
	 * @param g is the graphics container
	 * @param camera object of type Camera
	 * @throws SlickException 
	 */
	protected abstract void render(Graphics g,Camera camera) throws SlickException;
	
	protected Entity(float x_map, float y_map, boolean status, String path) throws SlickException {

		this.x_map = x_map;
		this.y_map = y_map;
		this.status = status;
		entity = new Image(path);
	}

	/*************************************************Getters/Setters*************************************************/
	public void setX_map(float x_map) {
		this.x_map = x_map;
	}

	public void setY_map(float y_map) {
		this.y_map = y_map;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public float getX_map() {
		return x_map;
	}

	public float getY_map() {
		return y_map;
	}
	/****************************************************************************************************************/

}
