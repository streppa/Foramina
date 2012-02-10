package st.fivepoints.foramina;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.getspout.spoutapi.block.SpoutBlock;

import st.fivepoints.foramina.material.Scaena;

public class ForaminaListener implements Listener {

  public ForaminaListener() {
    Foramina.instance.getServer().getPluginManager().registerEvents(this, Foramina.instance);
  }
  
  @EventHandler
  public void onBlockPlace( BlockPlaceEvent event ) {
    if ( event.isCancelled() ) return;
    
    Foramina.log("onBlockPlace");
    SpoutBlock blockAgainst = (SpoutBlock) event.getBlockAgainst();
    
    if ( blockAgainst.isCustomBlock() && blockAgainst.getCustomBlock() instanceof Scaena ) {
      event.setBuild(false);
    }
  }
  
  /** codename_B's BananaChunk plugin uses this ingenious method of forcing
   * Bukkit to refresh the chunk the player is teleporting into.
   */
  @EventHandler
  public void onPlayerTeleport( PlayerTeleportEvent event ) {
    if ( event.isCancelled() ) return;
    
    Player player = event.getPlayer();
    World world = player.getWorld();
    Chunk chunk = world.getChunkAt(event.getTo());
    int chunkx = chunk.getX();
    int chunkz = chunk.getZ();
    world.refreshChunk(chunkx, chunkz);
  
  }
}
