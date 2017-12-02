/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/*
 *     Type
 *     0 - Prince Aldric
 *     1 - Elvira
 *     2 = Garth
 */
public class Villager extends Unit{
	
	private static final int START_HP = 1, START_DAMAGE = 0, START_COOLDOWN = 0;
	private static final String PATH = "assets/units/";
	private static final String[][] NAMES_PHRASES = { {"Prince Aldric","Please seek out the\n Elixir of Life to\n cure the king.",
	"The elixir! My father is cured! Thank you!"} , {"Elvira","Return to me if you\n ever need healing.",
	"You're looking much\n healthier now."} , {"Garth","Find the Amulet of Vitality,\n across the river to\n the west."
	,"Find the Sword of\nStrength - cross the bridge to the east,\n then head south.",
	"Find the Tome of Agility,\n in the Land of Shadows.","You have found all the treasure I know of."} };
	
	private static final String[] VILLAGER_PATH ={"prince.png","shaman.png","peasant.png"} ;
	private int type;
	private boolean collide;
	private float xDraw, yDraw;

	/**
	 * Creates a new Villager
	 */
	public Villager(float type, float x_start, float y_start) throws SlickException {
		
		super(START_HP, START_DAMAGE, START_COOLDOWN,x_start,y_start,true,PATH+VILLAGER_PATH[(int)type]);
		this.type = (int) type;
		collide = false;	
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
		
		entity.drawCentered(x_map - camera.getxPos(), y_map - camera.getyPos());
		
		if (type == 0)
			healthRender(g,camera,NAMES_PHRASES[type][0],100);
		
		if (type == 1)
			healthRender(g,camera,NAMES_PHRASES[type][0],100);
		
		if (type == 2)
			healthRender(g,camera,NAMES_PHRASES[type][0],100);
	}
	
	/**
	 * Checks if the villager has collide the player
	 */
	public void hasCollide(Player player){
		
		double distX = x_map - player.getX_map(), distY = y_map - player.getY_map();
		double distance = sqrt( pow(distX,2) + pow(distY,2) );
		
		if (distance <= COLLISION_DISTANCE){
			collide = true;	
		}
	}
	
	/**
	 * Renders the dialogue of the villager
	 */
	public void renderDialogue(Player player, Graphics g,Camera camera){
		if(collide){
			xDraw = (x_map - camera.getxPos()) - 250;
			yDraw = (y_map - camera.getyPos()) - 70;
			
			if(type == 0)
				dialoguePrinceAldric(player,g,camera,xDraw,yDraw);
			if(type == 1)
				dialogueElvira(player,g,camera,xDraw,yDraw);
			if(type == 2)
				dialogueGarth(player,g,camera,xDraw,yDraw);	
		}
	}
	
	private void dialoguePrinceAldric(Player player, Graphics g, Camera camera,float xDraw, float yDraw){
		
		if(player.getPlayerItems()[3] == null)
			g.drawString(NAMES_PHRASES[type][1], xDraw, yDraw);
		else
			g.drawString(NAMES_PHRASES[type][2], xDraw, yDraw);
		
	}
	
	private void dialogueElvira(Player player, Graphics g, Camera camera, float xDraw, float yDraw){
		
		if(player.getHp() == player.getMaxHp()){
			g.drawString(NAMES_PHRASES[type][1], xDraw, yDraw);	
		}
		else{
			player.setHp(player.getMaxHp());
			g.drawString(NAMES_PHRASES[type][2],xDraw, yDraw);
		}
	}
	
	private void dialogueGarth(Player player, Graphics g, Camera camera, float xDraw, float yDraw){
		
		if(player.getPlayerItems()[0] == null){
			g.drawString(NAMES_PHRASES[type][1], xDraw, yDraw);
		}
		else if(player.getPlayerItems()[1] == null){
			g.drawString(NAMES_PHRASES[type][2], xDraw, yDraw);
		}
		else if(player.getPlayerItems()[2] == null){
			g.drawString(NAMES_PHRASES[type][3], xDraw, yDraw);
		}
		else {
			g.drawString(NAMES_PHRASES[type][4],xDraw, yDraw);
		}
	}
	
	/*******************************************Getters/Setters*********************************************/
	public boolean isCollide() {
		return collide;
	}
	
	public void setCollide(boolean collide) {
		this.collide = collide;
	}
	/*******************************************************************************************************/
}
