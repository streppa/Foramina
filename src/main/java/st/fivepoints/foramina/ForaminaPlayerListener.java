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
// import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
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

    @Override
    public void onPlayerInteract( PlayerInteractEvent event ) {
    	Action action = event.getAction();
    	Block  block  = event.getClickedBlock();
    	SpoutPlayer splayer = (SpoutPlayer) event.getPlayer();
    	
    	switch (action) {
    	  case RIGHT_CLICK_AIR:
    	    log.info("Right click air.");
    	    break;
        case RIGHT_CLICK_BLOCK:
          if ( block.getType() == Material.SAND ) {
            splayer.openScreen(ScreenType.PLAYER_INVENTORY);
          }
          break;
        case LEFT_CLICK_AIR:
          log.info("Left click air.");
          break;
        case LEFT_CLICK_BLOCK:
          log.info("Left click block.");
          break;
    	}

    	if ( event.hasBlock() ) {
    	  log.info("The block was a " + block.getType().toString());
    	}
    	
    }

    @Override
    public void onPlayerInteractEntity( PlayerInteractEntityEvent event ) {
    	log.info("process onPlayerInteractEntity");
    }
    
}
