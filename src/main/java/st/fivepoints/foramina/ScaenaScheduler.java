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

  private int teleportDelay;
  private int teleportCooldown;
  
  public ScaenaScheduler( int teleportDelay, int teleportCooldown ) {
    this.teleportDelay    = teleportDelay;
    this.teleportCooldown = teleportCooldown;
    
    Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Foramina.instance, this, 0, 10);
  }
  
  public void run() {
    List<ForaminaPlayer> players = ForaminaPlayer.getPlayers();

    for ( ForaminaPlayer player : players ) {
      SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player.getPlayer());
      ScaenaData scaena = ScaenaData.findScaenaByPlayer(player.getPlayer());
      
      if ( scaena == null ) {
        player.clearLastScaena();
        player.clearLastDestinationScaena();
        continue;
      }
      
      if ( player.getLastDestinationScaena() != null && scaena.equals(player.getLastDestinationScaena()) ) continue;
      
      if ( player.isCoolingDown(this.teleportCooldown) ) continue;

      if ( ! scaena.equals(player.getLastScaena()) ) player.startTimer();
      
      Location location = scaena.getLocation();
      if ( player.timerIsRunning() ) {
        if ( player.secondsSinceStart() > this.teleportDelay ) player.startTimer();
        
        if ( player.secondsSinceStart() == this.teleportDelay ) {
          scaena.getScaena().onTeleport(location, spoutPlayer);
        }
      } else {
        player.startTimer();
      }

      player.setLastScaena(scaena);
     }
    
  }

}
