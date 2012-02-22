package st.fivepoints.foramina;

import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.SpoutWorld;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.material.Scaena;

public class ScaenaScheduler implements Runnable {

  private int teleportDelayInTicks;
  private int teleportCooldownInTicks;
  
  private SpoutPlayer player;
  private Scaena      block;
  private Location    location;
  
  public ScaenaScheduler( int teleportDelay, int teleportCooldown ) {
    Foramina.log("Scheduler created.");
    this.teleportDelayInTicks    = teleportDelay * 20;
    this.teleportCooldownInTicks = teleportCooldown * 20;
    
    Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Foramina.instance, this, 0, 10);
  }
  
  public void run() {
    Foramina.log("Schehduler tick");
    List<ForaminaPlayer> players = ForaminaPlayer.getPlayers();
    
    for ( ForaminaPlayer player : players ) {
      Foramina.log("  " + player.getPlayer().getDisplayName());
      SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player.getPlayer());
      Location location = player.getPlayer().getLocation();
      SpoutBlock block = (SpoutBlock) player.getPlayer().getWorld().getBlockAt(location.clone().subtract(0,1,0));
      Foramina.log("  " + block.toString());
      ScaenaData scaena = ScaenaData.findScaenaByPlayerLocation(player.getPlayer());

      if ( scaena == null || ! player.cooldownIsFinished() ) continue;
      Foramina.log(spoutPlayer.getDisplayName() + " is on a Scaena.");
      if ( player.hasTimestamp(location) ) {
        Date timestamp = player.getTimestamp(location);
        long timeInSeconds = new Date().getTime() / 1000;
        long timestampInSeconds = timestamp.getTime() / 1000;
        
        if ( (timeInSeconds - timestampInSeconds) >= Foramina.teleportDelay ) {
          SpoutBlock playerBlock = (SpoutBlock) this.player.getLocation().getBlock();
          SpoutBlock blockUnderFoot = playerBlock.getRelative(BlockFace.DOWN);
          
          if ( blockUnderFoot.getLocation().equals(this.location) ) {
            if ( blockUnderFoot.isCustomBlock() && blockUnderFoot.getCustomBlock() == this.block ) {
              this.block.onTeleport(this.location, spoutPlayer);
            }
          }
          
        }
        
      } else {
        player.addTimestamp(location);
      }
      
    }
    
  }

}
