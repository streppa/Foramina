package st.fivepoints.foramina;

/*
    This file is part of Foramina

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
// import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.logging.Logger;

public class ForaminaPlayerListener extends PlayerListener {

  @SuppressWarnings("unused")
  private Foramina plugin;
  Logger log = Logger.getLogger("Minecraft");//Define your logger

  public ForaminaPlayerListener(Foramina plugin) {
    this.plugin = plugin;
  }

  // TODO: This needs to be replaced with a Scheduler. I want something like a three second delay before triggering the teleportation.
  @Override
  public void onPlayerMove( PlayerMoveEvent event ) {
    SpoutPlayer player    = (SpoutPlayer) event.getPlayer();
    SpoutBlock  blockUnderFoot = (SpoutBlock) player.getLocation().getBlock().getRelative(BlockFace.DOWN);
    if ( blockUnderFoot.isCustomBlock() && blockUnderFoot.getCustomBlock() instanceof ActivatorPad ) {
      this.plugin.log.info(player.getDisplayName() + " stepped on an Activator Pad.");
    }
  }
  
  @Override
  public void onPlayerInteract( PlayerInteractEvent event ) {
  	Action      action = event.getAction();
  	SpoutBlock  block  = (SpoutBlock) event.getClickedBlock();
   	SpoutPlayer player = (SpoutPlayer) event.getPlayer();

   	if ( action == Action.RIGHT_CLICK_BLOCK && block.isCustomBlock() && block.getCustomBlock() instanceof ActivatorPad ) {
   	  this.plugin.log.info(player.getDisplayName() + " right-clicked on an Activator Pad.");
   	  return;
   	}
    	
  }

}
