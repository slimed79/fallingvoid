package fr.gravenilvec.fallingvoid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FallingVoid extends JavaPlugin{
	
	private Location spawn, gamespawn;
	
	public List<FallingChunck> chunksocle = new ArrayList<>();
	public List<FallingChunck> chunkplateform = new ArrayList<>();
	public List<Block> socle = new ArrayList<>();
	public List<Block> plateform = new ArrayList<>();
	
	private WorldBorder border;
	private FallingState current;
	private String gamename = "fallingworld";
	public World gameworld;

	public List<Player> playersList = new ArrayList<>();
	public List<Integer> blocks = new ArrayList<>();
	
	@Override
	public void onEnable() {
		setState(FallingState.WAITING);
		
		Bukkit.unloadWorld(gamename, false);
		deleteWorld(new File(gamename));
		deleteWorld(new File(gamename));
		
		saveDefaultConfig();
		spawn = getParseLoc(getConfig().getString("spawn"), true);
		blocks = getConfig().getIntegerList("blocks.plateformBlocksId");
		
		WorldCreator wc = new WorldCreator(gamename);
		wc.type(WorldType.FLAT);
		wc.generator(new FallingGenerator(this));
		wc.createWorld();
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				loadWorld();
				for(Player pls : Bukkit.getOnlinePlayers()){
					PlayerJoinEvent event = new PlayerJoinEvent(pls, null);
					Bukkit.getPluginManager().callEvent(event);
				}
				if(chunkplateform.size() != 6400) Bukkit.reload();
			}
		}.runTaskLater(this, 25);
		
		getServer().getPluginManager().registerEvents(new FallingListeners(this), this);
		
	}
	
	@Override
	public void onDisable() {
		Bukkit.unloadWorld(gamename, false);
		deleteWorld(new File(gamename));
		deleteWorld(new File(gamename));
	}

	private void deleteWorld(File path) {
		System.out.println("Deleting... world");
		if(path.exists()){
			File files[] = path.listFiles();
			
			for(int i = 0; i < files.length; i++){
				if(files[i].isDirectory()){
					deleteWorld(files[i]);
				}else{
					files[i].delete();
				}
			}
			
		}
	}
	
	protected void loadWorld() {
		gameworld = Bukkit.getWorld(gamename);
		gamespawn = getParseLoc(getConfig().getString("gamespawn"), false);
		border = gameworld.getWorldBorder();
		border.setCenter(gamespawn);
		border.setSize(getConfig().getDouble("borders.defaultSize"));
		System.out.println("[FallingVoid] Server Ready !");
	}

	protected Location getParseLoc(String string, boolean defaultspawn) {
		String[] parse = string.split(",");
		double x = Double.valueOf(parse[0]);
		double y = Double.valueOf(parse[1]);
		double z = Double.valueOf(parse[2]);
		float yaw = Float.valueOf(parse[3]);
		float pitch = Float.valueOf(parse[4]);
	
		World game = Bukkit.getWorld("world");
		if(!defaultspawn)  game = gameworld;
		
		return new Location(game, x, y, z, yaw, pitch);
	}

	public WorldBorder getBorder(){
		return border;
	}
	
	public void setState(FallingState state){
		this.current = state;
	}
	
	public boolean isState(FallingState state){
		return current == state;
	}

	public Location getSpawn() {
		return spawn;
	}
	
	public Location getGameSpawn() {
		return gamespawn;
	}

	public void eliminate(Player player) {
		if(playersList.contains(player)){
			playersList.remove(player);
		}
		
		if(isState(FallingState.WAITING)) return;
		
		player.setGameMode(GameMode.SPECTATOR);
		
		if(playersList.size() == 1){
			Player winner = playersList.get(0);
			Bukkit.broadcastMessage(get("playerWin").replace("<winner>", winner.getName()));
			player.teleport(winner);
			playersList.remove(winner);
			setState(FallingState.FINISH);
		}
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for(Player pls : Bukkit.getOnlinePlayers()){
					pls.kickPlayer(get("restarting"));
				}

				Bukkit.reload();
				
			}
		}.runTaskLater(this, 100);
		
	}

	public String get(String string) {
		return getConfig().getString("messages." + string).replace("&", "§");
	}

	
}
