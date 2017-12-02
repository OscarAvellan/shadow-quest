/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Oscar Avellan <oavellan>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class World {
	
	public static final int X_RENDER_TILES = 13, Y_RENDER_TILES = 10, TILE_WIDTH = 72,TILE_HEIGHT = 72;
	private final int DIALOGUE_TIME = 3000;
	private final String PATH_MAP = "assets/map.tmx", ASSETS = "assets";
	
	private int block_x, block_y,playerXTile,playerYTile;
	private int xTilePos, yTilePos, tileID, x_cam, y_cam,dialogueTimer;
	private int block_l,block_u,block_r,block_d,objectXTile, objectYTile;
	private TiledMap tiledmap;
	private Camera camera;
	private Player player;
	private int playerAttackTimer = 0;

	/**************************************************** PassiveMonster **************************************************************/
	/**
	 * Initial positions of the passive monsters
	 */
	private PassiveMonster[] passiveMonsterArray;
	private float[][] passiveMonsterStartPos = { { 1431, 864 }, { 1154, 1321 }, { 807, 2315 }, { 833, 2657 },
			{ 1090, 3200 }, { 676, 3187 }, { 836, 3966 }, { 653, 4367 }, { 1343, 2731 }, { 1835, 3017 }, { 1657, 3954 },
			{ 1054, 5337 }, { 801, 5921 }, { 560, 6682 }, { 1275, 5696 }, { 1671, 5991 }, { 2248, 6298 },
			{ 2952, 6373 }, { 3864, 6695 }, { 4554, 6443 }, { 4683, 6228 }, { 5312, 6606 }, { 5484, 5946 },
			{ 6371, 5634 }, { 5473, 3544 }, { 5944, 3339 }, { 6301, 3414 }, { 6388, 1994 }, { 6410, 1584 },
			{ 5314, 274 } };
	/*********************************************************************************************************************************/

	/**************************************************** AgressiveMonster **************************************************************/
	/**
	 * Initial positions of the agressive monsters
	 */
	private AgressiveMonster[] agressiveMonsterArray;
	private float[][] agressiveMonsterStartPos = { { 0, 681, 3201 }, { 0, 691, 4360  }, { 0, 2166, 2650 },
			{ 0, 2122, 2725 }, { 0, 2284, 2962 }, { 0, 2072, 4515 }, { 0, 2006, 4568 }, { 0, 2385, 4629 },
			{ 0, 2446, 4590 }, { 0, 2517, 4532 }, { 0, 4157, 6730 }, { 0, 4247, 6620 }, { 0, 4137, 6519 },
			{ 0, 4234, 6449 }, { 0, 5879, 4994 }, { 0, 5954, 4928 }, { 0, 6016, 4866 }, { 0, 5860, 4277 },
			{ 0, 5772, 4277 }, { 0, 5715, 4277 }, { 0, 5653, 4277 }, { 0, 5787, 797  }, { 0, 5668, 720  },
			{ 0, 5813, 454  }, { 0, 5236, 917  }, { 0, 5048, 1062 }, { 0, 4845, 996  }, { 0, 4496, 575  }, { 0, 3457, 273 },
			{ 0, 3506, 779  }, { 0, 3624, 1192 }, { 0, 2931, 1396 }, { 0, 2715, 1326 }, { 0, 2442, 1374 },
			{ 0, 2579, 1159 }, { 0, 2799, 1269 }, { 0, 2768, 739  }, { 0, 2099, 956  }, { 1, 1889, 2581 },
			{ 1, 4502, 6283 }, { 1, 5248, 6581 }, { 1, 5345, 6678 }, { 1, 5940, 3412 }, { 1, 6335, 3459 },
			{ 1, 6669, 260  }, { 1, 6598, 339  }, { 1, 6598, 528  }, { 1, 6435, 528  }, { 1, 6435, 678  }, { 1, 5076, 1082 },
			{ 1, 5191, 1187 }, { 1, 4940, 1175 }, { 1, 4760, 1039 }, { 1, 4883, 889  }, { 1, 4427, 553  },
			{ 1, 3559, 162  }, { 1, 3779, 1553 }, { 1, 3573, 1553 }, { 1, 3534, 2464 }, { 1, 3635, 2464 },
			{ 1, 3402, 2861 }, { 1, 3151, 2857 }, { 1, 3005, 2997 }, { 1, 2763, 2263 }, { 1, 2648, 2263 },
			{ 1, 2621, 1337 }, { 1, 2907, 1270 }, { 1, 2331, 598  }, { 1, 2987, 394  }, { 1, 1979, 394  },
			{ 1, 2045, 693  }, { 1, 2069, 1028 }, { 2, 1255, 2924 }, { 2, 2545, 4708 }, { 2, 4189, 6585 },
			{ 2, 5720, 622  }, { 2, 5649, 767  }, { 2, 5291, 312  }, { 2, 5256, 852  }, { 2, 4790, 976  }, { 2, 4648, 401 },
			{ 2, 3628, 1181 }, { 2, 3771, 1181 }, { 2, 3182, 2892 }, { 2, 3116, 3033 }, { 2, 2803, 2901 },
			{ 2, 2850, 2426 }, { 2, 2005, 1524 }, { 2, 2132, 1427 }, { 2, 2242, 1343 }, { 2, 2428, 771  },
			{ 2, 2427, 907  }, { 2, 2770, 613  }, { 2, 2915, 477  }, { 2, 1970, 553  }, { 2, 2143, 1048 },
			{ 3, 2069, 510  } };
	/*********************************************************************************************************************************/

	/**************************************************** Villagers*******************************************************************/
	/**
	 * Initial positions of the villagers
	 */
	private Villager[] villagerArray;
	private float[][] villagerStartPos = { {0,467,679},{1,738,549},{2,756,870} };
	/*********************************************************************************************************************************/
	
	/**************************************************** Item ***********************************************************************/
	/**
	 * Initial positions of the items
	 */
	private Item[] itemArray;
	private float[][] itemPos = { {0,965, 3563},{1,546, 6707},{2,4791, 1253},{3,1976, 402} };
	/*********************************************************************************************************************************/
	
	/*************************************************CONSTRUCTOR****************************************************/
	public World() throws SlickException {
		
		tiledmap = new TiledMap(PATH_MAP, ASSETS);
		player = new Player();
		camera = new Camera(player, RPG.SCREENWIDTH, RPG.SCREENHEIGHT);
		dialogueTimer = DIALOGUE_TIME;
		
		passiveMonsterArray = new PassiveMonster[passiveMonsterStartPos.length];
		agressiveMonsterArray = new AgressiveMonster[agressiveMonsterStartPos.length];
		villagerArray = new Villager[villagerStartPos.length];
		itemArray = new Item[itemPos.length];
	
		/**
		 * Initialising the monsters, villagers and items
		 */
		for (int i = 0; i < passiveMonsterStartPos.length; i++) {
			passiveMonsterArray[i] = new PassiveMonster(passiveMonsterStartPos[i][0], passiveMonsterStartPos[i][1]);
		}

		for (int i = 0; i < agressiveMonsterStartPos.length; i++) {
			agressiveMonsterArray[i] = new AgressiveMonster(agressiveMonsterStartPos[i][0], agressiveMonsterStartPos[i][1],
					agressiveMonsterStartPos[i][2]);
		}
		
		for(int i = 0; i < villagerStartPos.length; i++){
			villagerArray[i] = new Villager(villagerStartPos[i][0], villagerStartPos[i][1], villagerStartPos[i][2]);
		}
		
		for(int i = 0; i < itemPos.length; i++){
			itemArray[i] = new Item(itemPos[i][0],itemPos[i][1],itemPos[i][2]);
		}

	}
	/****************************************************************************************************************/
	
	/*************************************************UPDATE****************************************************/
	public void update(double dir_x, double dir_y, int delta, boolean attack) throws SlickException {
		
		checkTilesPlayer(dir_x,dir_y);
		player.update(dir_x, dir_y, delta,block_x,block_y,0,0);
		camera.update();
		player.hasCollide(itemArray);
		player.useItems(player);
		
		if(playerAttackTimer > 0){
			playerAttackTimer -= delta;
			if(playerAttackTimer < 0 )
				playerAttackTimer = 0;	
		}
		
		if(attack && playerAttackTimer <= 0){
			player.hasCollide(agressiveMonsterArray);
			player.hasCollide(passiveMonsterArray);
			playerAttackTimer = player.getCooldown();
		}
		
		for (PassiveMonster monster : passiveMonsterArray) {
			if(monster.isStatus()){
				checkTiles(monster);
				if(monster.isAttacked() && monster.getAttackedTimer() <= PassiveMonster.getTimeNotAttacked()){
					monster.runAway(player,delta,block_l,block_u,block_r,block_d);
					monster.setAttackedTimer(monster.getAttackedTimer() + delta);
				}
				else if(monster.isAttacked() && monster.getAttackedTimer() > PassiveMonster.getTimeNotAttacked()){
					monster.setAttacked(false);
					monster.setAttackedTimer(0);
				}
				else{
					monster.update(0,0,delta,block_l,block_u,block_r,block_d);
				}	
					 }
		}
		
		for (AgressiveMonster monster : agressiveMonsterArray) {
			if(monster.isStatus())
				monster.hasCollide(player,delta);
		}
		
		for (Villager villager : villagerArray) {
			villager.hasCollide(player);
			
			if(villager.isCollide() && dialogueTimer > 0){
				dialogueTimer -= delta;
			}
			else if(villager.isCollide() && dialogueTimer <= 0){
				villager.setCollide(false);
				dialogueTimer = DIALOGUE_TIME;
			}
		}
		checkPlayerHp(player,itemArray);
	}
	
	
	/***********************************************************************************************************/

	/**
	 * Checks the tiles of the monsters
	 */
	private void checkTiles(PassiveMonster monster){
		
		objectXTile = (int)(monster.getX_map() / TILE_WIDTH);
		objectYTile = (int)(monster.getY_map() / TILE_HEIGHT);
		
		if(objectXTile > 0 && objectYTile > 0 && objectXTile < 93 && objectYTile < 93){
			tileID = tiledmap.getTileId(objectXTile - 1 ,objectYTile, 0);
			block_l = Integer.parseInt( tiledmap.getTileProperty(tileID, "block", "0") );
			
			tileID = tiledmap.getTileId(objectXTile,objectYTile - 1, 0);
			block_u = Integer.parseInt( tiledmap.getTileProperty(tileID, "block", "0") );
		
			tileID = tiledmap.getTileId(objectXTile + 1 ,objectYTile, 0);
			block_r = Integer.parseInt( tiledmap.getTileProperty(tileID, "block", "0") );
		
			tileID = tiledmap.getTileId(objectXTile,objectYTile + 1, 0);
			block_d = Integer.parseInt( tiledmap.getTileProperty(tileID, "block", "0") );}
		
		/**
		 * Checks that the tiles have valid indexes
		 */
		if(objectXTile < 1)
			block_l = 1;
		
		if(objectYTile < 1)
			block_u = 1;
		
		if(objectXTile > 92)
			block_r = 92;
		
		if(objectXTile > 92)
			block_d = 92;
	}
	
	/**
	 * Checks the player's tiles
	 */
	private void checkTilesPlayer(double dir_x, double dir_y){
		
		playerXTile = (int)(player.getX_map() / TILE_WIDTH);
		playerYTile = (int)(player.getY_map() / TILE_HEIGHT);
		
		if(dir_x > 0)
			xTilePos = playerXTile + 1;
		else
			xTilePos = playerXTile - 1;
		
		if(dir_y > 0)
			yTilePos = playerYTile + 1;
		else
			yTilePos = playerYTile - 1;
		
		tileID = tiledmap.getTileId(xTilePos,playerYTile, 0);
		block_x = Integer.parseInt( tiledmap.getTileProperty(tileID, "block", "0") );
		
		tileID = tiledmap.getTileId(playerXTile, yTilePos, 0);
		block_y = Integer.parseInt( tiledmap.getTileProperty(tileID, "block", "0") );
	}
	
	/*************************************************RENDER****************************************************/
	public void render(Graphics g) throws SlickException {
		x_cam = camera.getxPos();
		y_cam = camera.getyPos();
		

		// The x_cam/72 gives the index of the tiles to start rendering at, and
		// the -x_cam%72 gives
		// how much to the left the camera should move to centre the player.
		tiledmap.render(-x_cam % TILE_WIDTH, -y_cam % TILE_HEIGHT, x_cam / TILE_WIDTH,
				y_cam / TILE_WIDTH, X_RENDER_TILES, Y_RENDER_TILES);
		
		player.renderPanel(g);
		player.render(g, camera);
		
		for (PassiveMonster monster : passiveMonsterArray) {
			if(monster.isStatus())
				monster.render(g, camera);
		}
		
		for (AgressiveMonster monster : agressiveMonsterArray) {
			if(monster.isStatus())
				monster.render(g, camera);
		}
		
		for (Villager villager : villagerArray) {
			villager.render(g, camera);
			villager.renderDialogue(player, g, camera);
		}
		
		for (Item item : itemArray) {
			item.render(g, camera);
		}

	}
	/***********************************************************************************************************/
	/**
	 * Checks the player's hp and if the player's status is false, reinitialises its position and attributes
	 */
	public void checkPlayerHp(Player player, Item[] itemArray){
		if (!player.isStatus()){
			player.setX_map(player.getX_START());
			player.setY_map(player.getY_START());
			player.setTemp_x(player.getX_START());
			player.setTemp_y(player.getY_START());
			player.setMaxHp(player.getSTART_HP());
			player.setHp(player.getSTART_HP());
			player.setCooldown(player.getSTART_COOLDOWN());
			player.setDamage(player.getSTART_DAMAGE());
			player.setStatus(true);
			
			for(int i = 0; i < itemArray.length; i++){
				player.getPlayerItems()[i] = null;
			}
			
			for(Item item : itemArray){
				item.setStatus(true);
				item.setUsed(false);
			}
		}
	}
    
}
