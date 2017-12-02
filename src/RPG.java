
/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Matt Giuca <mgiuca>
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Main class for the Role-Playing Game engine. Handles initialisation, input
 * and rendering.
 */
public class RPG extends BasicGame {
	private World world;

	public static final int SCREENWIDTH = 800;
	public static final int SCREENHEIGHT = 600;
	public static final int PANELHEIGHT = 70;

	/** Create a new RPG object. */
	public RPG() {
		super("RPG Game Engine");
	}

	/**
	 * Initialise the game state.
	 * 
	 * @param gc
	 *            The Slick game container object.
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		world = new World();
	}

	/**
	 * Update the game state for a frame.
	 * 
	 * @param gc
	 *            The Slick game container object.
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		Input input = gc.getInput();

		double dir_x = 0;
		double dir_y = 0;
		boolean attack = false;

		if (input.isKeyDown(Input.KEY_DOWN))
			dir_y += 1;
		if (input.isKeyDown(Input.KEY_UP))
			dir_y -= 1;
		if (input.isKeyDown(Input.KEY_LEFT))
			dir_x -= 1;
		if (input.isKeyDown(Input.KEY_RIGHT))
			dir_x += 1;
		if(input.isKeyDown(Input.KEY_A))
			attack = true;
		if (input.isKeyDown(Input.KEY_ESCAPE))
			gc.exit();
		

		world.update(dir_x, dir_y, delta,attack);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		world.render(g);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new RPG());
		// setShowFPS(true), to show frames-per-second.
		app.setShowFPS(false);
		app.setDisplayMode(SCREENWIDTH, SCREENHEIGHT, false);
		app.start();
	}
}
