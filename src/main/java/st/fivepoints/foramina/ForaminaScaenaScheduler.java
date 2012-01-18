package st.fivepoints.foramina;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ForaminaScaenaScheduler implements Runnable {

  private Foramina plugin;
  
  private Map<SpoutPlayer, Long> activatorEpoch = new HashMap<SpoutPlayer, Long>();
  private int taskId;
  private int activationDelayInSeconds;
  
  public ForaminaScaenaScheduler( Foramina plugin, int delay ) {
    this.plugin = plugin;
    this.activationDelayInSeconds = delay;
    this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, this, 0, 5); // Start immediately and run every half second.
  }
  
  public ForaminaScaenaScheduler( Foramina plugin ) {
    this( plugin, 3 );
  }
  
  @Override
  public void run() {
    
    Player[] players = Bukkit.getServer().getOnlinePlayers();
    for ( Player player : players ) {
      SpoutPlayer splayer = SpoutManager.getPlayer(player);
      SpoutBlock  blockUnderFoot = (SpoutBlock) splayer.getLocation().getBlock().getRelative(BlockFace.DOWN);
      
      if ( blockUnderFoot.isCustomBlock() && blockUnderFoot.getCustomBlock() instanceof ForaminaScaena ) {
        // this.plugin.log.info(splayer.getDisplayName() + " is standing on an activator pad.");

        if ( this.hasPlayerTimer(splayer) ) {
          int currentDelay = this.getPlayerTimer(splayer);

          if ( currentDelay == this.activationDelayInSeconds ) {
            // Other checks for teleportation!
            this.plugin.log.info(splayer.getDisplayName() + " has been on a activator pad for " + this.activationDelayInSeconds + " second(s).");
            splayer.sendMessage("You've been on this activator pad for 3 seconds. You should get teleported!");
            this.resetPlayerTimer(splayer);
          } else if ( currentDelay > this.activationDelayInSeconds ) {
            this.resetPlayerTimer(splayer);
            this.startPlayerTimer(splayer);
          }
          
        } else {
          this.startPlayerTimer(splayer);
        }
        
      }
    }
  }

  public boolean hasPlayerTimer( SpoutPlayer splayer ) {
    return this.activatorEpoch.containsKey(splayer);
  }
  
  public void startPlayerTimer( SpoutPlayer splayer ) {
    this.activatorEpoch.put(splayer, this.getEpoch());
    this.plugin.log.info(splayer.getDisplayName() + " activator pad timer has started.");
  }
  
  public int getPlayerTimer( SpoutPlayer splayer ) {
    return (int) (this.getEpoch() - this.activatorEpoch.get(splayer));
  }
  
  public void resetPlayerTimer( SpoutPlayer splayer ) {
    this.activatorEpoch.remove(splayer);
    this.plugin.log.info(splayer.getDisplayName() + " activator pad timer has gone stale.");
  }
  
  public long getEpoch() {
    return System.currentTimeMillis() / 1000;
  }
}
