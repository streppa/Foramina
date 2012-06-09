/**
 * Foramina
 * Copyright (C) 2012 Scott Treppa <streppa@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package st.fivepoints.foramina;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
    
    SpoutBlock blockAgainst = (SpoutBlock) event.getBlockAgainst();
    
    if ( blockAgainst.isCustomBlock() && blockAgainst.getCustomBlock() instanceof Scaena ) {
      event.setBuild(false);
    }
  }
  
  /** codename_B's BananaChunk plugin uses this ingenious method of forcing
   * Bukkit to refresh the chunk the player is teleporting into. Woot.
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
  
/*
  @EventHandler
  public void onPlayerInteract( PlayerInteractEvent event ) {
    if ( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
      Location location = event.getClickedBlock().getLocation();
      if (event.getPlayer().isOp() )
        event.getPlayer().sendMessage("&3x: &b" + location.getX() + "  &3y: &b" + location.getY() + " &3z: &b" + location.getZ());
    }
  }
*/
}
