package st.fivepoints.foramina;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ForaminaScaenaScheduler implements Runnable {

  private Plugin plugin;
  
  private int taskId;
  private int activationDelayInSeconds;
  private int activationDelayInTicks;
  
  private SpoutPlayer     player;
  private ForaminaScaena  block;
  private Location        location;
  
  Logger log = Logger.getLogger("Minecraft");
  
  public ForaminaScaenaScheduler( Plugin plugin, SpoutPlayer player, ForaminaScaena block, Location location, int delay ) {
    this.plugin = plugin;
    this.activationDelayInSeconds = delay;
    this.activationDelayInTicks   = delay * 20;
    this.player = player;
    this.block  = block;
    this.location = location;
    this.taskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, this, this.activationDelayInTicks);
  }
  
  @Override
  public void run() {
    this.player.teleport(this.location.clone().add(10, 1, 10));
  }

}
