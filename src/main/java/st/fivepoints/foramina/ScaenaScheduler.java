package st.fivepoints.foramina;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.material.Scaena;

public class ScaenaScheduler implements Runnable {

  private int activationDelayInTicks;
  
  private SpoutPlayer player;
  private Scaena      block;
  private Location    location;
  
  public ScaenaScheduler( SpoutPlayer player, Scaena block, Location location, int delay ) {
    this.activationDelayInTicks   = delay * 20;
    this.player = player;
    this.block  = block;
    this.location = location;
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Foramina.instance, this, this.activationDelayInTicks);
  }
  
  public void run() {
    SpoutBlock playerBlock = (SpoutBlock) this.player.getLocation().getBlock();
    SpoutBlock blockUnderFoot = playerBlock.getRelative(BlockFace.DOWN);
    if ( blockUnderFoot.getLocation().equals(this.location) ) {
      if ( blockUnderFoot.isCustomBlock() && blockUnderFoot.getCustomBlock() == this.block ) {
        this.block.onTeleport(this.location, player);
      }
    }
  }

}