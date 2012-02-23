package st.fivepoints.foramina;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.material.Scaena;

public class ForaminaListener implements Listener {

  public ForaminaListener() {
    Foramina.instance.getServer().getPluginManager().registerEvents(this, Foramina.instance);
  }
  
  @EventHandler
  public void onBlockPlace( BlockPlaceEvent event ) {
    if ( event.isCancelled() ) return;
    
    SpoutBlock blockAgainst = (SpoutBlock) event.getBlockAgainst();
    
    if ( blockAgainst.isCustomBlock() && blockAgainst.getCustomBlock() instanceof Scaena ) {
      event.setBuild(false);
    }
  }
  
  /** codename_B's BananaChunk plugin uses this ingenious method of forcing
   * Bukkit to refresh the chunk the player is teleporting into.
   */
 /* @EventHandler
  public void onPlayerTeleport( PlayerTeleportEvent event ) {
    if ( event.isCancelled() ) return;
    
    Player player = event.getPlayer();
    World world = player.getWorld();
    Chunk chunk = world.getChunkAt(event.getTo());
    int chunkx = chunk.getX();
    int chunkz = chunk.getZ();
    world.refreshChunk(chunkx, chunkz);

  }*/
  
  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerLogin( PlayerLoginEvent event ) {
    ForaminaPlayer.create(event.getPlayer());
  }
  
  @EventHandler
  public void onPlayerLogout( PlayerQuitEvent event ) {
    ForaminaPlayer foraminaPlayer = ForaminaPlayer.findByPlayer(event.getPlayer());
    if ( foraminaPlayer == null ) return;
    ForaminaPlayer.destroy(foraminaPlayer);
  }
}
