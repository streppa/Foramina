package st.fivepoints.Foramina;

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

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.logging.Logger;

public class ForaminaPlayerListener extends PlayerListener {

    private Foramina plugin;
    Logger log = Logger.getLogger("Minecraft");//Define your logger

    public ForaminaPlayerListener(Foramina plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerInteract( PlayerInteractEvent event ) {
    	
    }

    @Override
    public void onPlayerInteractEntity( PlayerInteractEntityEvent event ) {
    	
    }
    
}
