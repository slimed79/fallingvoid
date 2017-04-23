package fr.gravenilvec.fallingvoid.tasks;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.gravenilvec.fallingvoid.FallingChunck;
import fr.gravenilvec.fallingvoid.FallingState;
import fr.gravenilvec.fallingvoid.FallingVoid;

public class FallingAutoStart extends BukkitRunnable{
	
	private FallingVoid main;
	private int timer = 10;

	public FallingAutoStart(FallingVoid main) { 
		this.main = main; 
		this.timer = main.getConfig().getInt("autostart.timer");
	}

	@Override
	public void run() {
		
		for(Player pls : Bukkit.getOnlinePlayers()){
			pls.setLevel(timer);
		}
		
		if(timer == 15 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
			Bukkit.broadcastMessage(main.get("prefix") + main.get("autoStart.timerSecond").replace("<timer>", timer+""));
		}
		
		if(timer == 0){
			Bukkit.broadcastMessage(main.get("prefix") + main.get("autoStart.timerEnd"));
			for(Player pls : main.playersList){
				pls.setGameMode(GameMode.SURVIVAL);
				pls.teleport(main.getGameSpawn());
				pls.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
			}
			cancel();
			FallingGameCycle cycle = new FallingGameCycle(main);
			cycle.runTaskTimer(main, 0, 20);
			main.setState(FallingState.PREGAME);
			
			Iterator<FallingChunck> ssocle = main.chunksocle.iterator();
			while(ssocle.hasNext()){
				FallingChunck ch = ssocle.next();
				main.socle.add(main.gameworld.getChunkAt(ch.getChunkX(), ch.getChunkZ()).getBlock(ch.getX(), ch.getY(), ch.getZ()));
			}
			
			Iterator<FallingChunck> splat = main.chunkplateform.iterator();
			while(splat.hasNext()){
				FallingChunck ch = splat.next();
				main.plateform.add(main.gameworld.getChunkAt(ch.getChunkX(), ch.getChunkZ()).getBlock(ch.getX(), ch.getY(), ch.getZ()));
			}
			
		}
		
		timer--;
	}
}
