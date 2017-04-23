
package fr.gravenilvec.fallingvoid.tasks;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.gravenilvec.fallingvoid.FallingState;
import fr.gravenilvec.fallingvoid.FallingVoid;
import fr.gravenilvec.fallingvoid.utils.Title;

public class FallingGameCycle extends BukkitRunnable{

	private int timer = 120;
	private FallingVoid main;

	public FallingGameCycle(FallingVoid main) {
		this.main = main; 
		this.timer = main.getConfig().getInt("gamecycle.timerUntilPvP");
	}

	@Override
	public void run() {
		
		String format = new SimpleDateFormat("mm:ss").format(timer * 1000);
		
		if(main.getConfig().getBoolean("chrono.giveStone") == true){
			for(Player pls : Bukkit.getOnlinePlayers()){
				pls.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 1));
			}
		}
		
		if(timer == 0){
			
			if(main.isState(FallingState.PREGAME)){
				main.setState(FallingState.GAME);
				Bukkit.broadcastMessage(main.get("prefix") + main.get("pvpEnable"));
				
				Iterator<Block> socle = main.socle.iterator();
				while(socle.hasNext()){
					socle.next().setType(Material.AIR);
				}
				
				timer = main.getConfig().getInt("gamecycle.timerUntilBorders");
				
			}else{
				Bukkit.broadcastMessage(main.get("prefix") + main.get("decreaseBorders"));
			}
			
		}
		
		if(main.isState(FallingState.PREGAME)){
			for(Player pls : Bukkit.getOnlinePlayers()){
				Title.sendActionBar(pls, main.get("actionBar.destroyPlateform").replace("<format>", format));
			}
		}
		
		//destroy plateform
		if(main.isState(FallingState.GAME)){
			
			for(int i = 0; i < 50; i++){
				if(main.plateform.size() <= 0)return;
				
				Block b = main.plateform.get(new Random().nextInt(main.plateform.size()));
				b.setType(Material.AIR);
				main.plateform.remove(b);
			}
			

			for(Player pls : Bukkit.getOnlinePlayers()){
				Title.sendActionBar(pls, main.get("actionBar.borders").replace("<format>", format));
			}
			
		}
		
		//border
		if(main.isState(FallingState.GAMEBORDER)){
			WorldBorder border = main.getBorder();
			if(border.getSize() > main.getConfig().getDouble("borders.minSize")){
				border.setSize(border.getSize() - main.getConfig().getDouble("borders.decreasePerSecond"));
			}
			
		}
		
		timer--;
	}
	
}
