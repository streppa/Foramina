package st.fivepoints.foramina.task;

import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.SpoutWorld;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.ForaminaPlayer;
import st.fivepoints.foramina.ScaenaData;
import st.fivepoints.foramina.material.Scaena;

public class WatchScaenus implements Runnable {

  // TODO: Make 'defaultInterval' configurable;
  private static int defaultInterval = 3;
  
  private int intervalInSeconds;
  private int intervalInTicks;
  
  public WatchScaenus( int intervalInSeconds ) {
    Foramina.log("Scheduler created.");
    
    this.intervalInSeconds = intervalInSeconds;
    this.intervalInTicks   = this.intervalInSeconds * 20;
    
    Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Foramina.instance, this, 0, this.intervalInTicks);
  }
  
  public WatchScaenus() {
    this( defaultInterval );
  }
  
  public void run() {
    Foramina.log("\n");
    Foramina.log("Watching the Scaenus");
    
    List<ForaminaPlayer> players = ForaminaPlayer.getPlayers();
   
    for ( ForaminaPlayer player : players ) {
      SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player.getPlayer());
      ScaenaData scaena       = ScaenaData.findScaenaByPlayer(player.getPlayer());
      
      if ( scaena == null ) continue;
      
      SpoutBlock block = scaena.getBlock();
     // scaena.getScaena().onPlayerTreadAt(block.getWorld(), block.getX(), block.getY(), block.getZ(), spoutPlayer);
     }
    
  }

}
