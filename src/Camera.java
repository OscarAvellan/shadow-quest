
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import org.newdawn.slick.SlickException;

/**
 * Represents the camera that controls our viewpoint.
 */
public class Camera {

	/** The unit this camera is following */
	private static final int mapWidth = 96;
	private static final int mapHeight = 96;

	/** The camera's position in the world, in x and y coordinates. */
	private int xPos, temp_xPos, tempXTile;
	private int yPos, temp_yPos, tempYTile;
	private Player unitFollow;

	/** Create a new Camera object. */
	public Camera(Player player, int screenwidth, int screenheight) {

		unitFollow = player;

		// The player position minus half of the screen gives the
		// x and y camera position
		xPos = (int) unitFollow.getX_map() - screenwidth / 2;
		yPos = (int) unitFollow.getY_map() - screenheight / 2;
	}

	/**
	 * Update the game camera to recentre it's viewpoint around the player
	 */
	public void update() throws SlickException {
		temp_xPos = (int) unitFollow.getX_map() - RPG.SCREENWIDTH / 2;
		temp_yPos = (int) unitFollow.getY_map() - RPG.SCREENHEIGHT / 2;
		
		tempXTile = temp_xPos / World.TILE_WIDTH;
		tempYTile = temp_yPos / World.TILE_HEIGHT;

		// Make sure the camera doesn't go out of the map
		if (tempXTile >= 0  && tempXTile <= (mapWidth - World.X_RENDER_TILES))
			xPos = temp_xPos;

		if (tempYTile >= 0 && tempYTile <= (mapHeight - World.Y_RENDER_TILES))
			yPos = temp_yPos;
	}
	
	/****************************************Getters/Setters****************************************/
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}
	/*********************************************************************************************/

	/**
	 * Tells the camera to follow a given unit.
	 */
	public void followUnit(Object unit) throws SlickException {
		// TO DO: Fill In
	}

}