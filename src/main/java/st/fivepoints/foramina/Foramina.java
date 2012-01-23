
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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.material.CustomBlock;

// import java.util.HashMap;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class Foramina extends JavaPlugin {

  public static ForaminaScaena scaena;
  public static ForaminaScaenaScheduler foraminaScaenaScheduler;
  public static ForaminaPersistence db;
  
  private final ForaminaCommandExecutor commandExecutor = new ForaminaCommandExecutor(this);
  private final ForaminaPlayerListener   playerListener = new ForaminaPlayerListener(this);
  private final ForaminaBlockListener    blockListener  = new ForaminaBlockListener(this);                

  public Location playerLoc;
  Logger log = Logger.getLogger("Minecraft");


  public void onDisable() {
    scaena.close();
    this.db.disconnect();
    log.info(this.toString() + "disabled.");
  }

  public void onEnable() {
    log.info("Enabling " + this.toString( ));

    PluginManager pm = this.getServer().getPluginManager();

    getCommand("foramina").setExecutor(commandExecutor);
    
    // pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
    pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Normal, this);
    // pm.registerEvent(Event.Type.BLOCK_CANBUILD, blockListener, Event.Priority.Normal, this);

    // foraminaScaenaScheduler = new ForaminaScaenaScheduler(this);
    
    this.getConfig().options().copyDefaults(true);
    saveConfig();
    
    db = new ForaminaPersistence(this, "foramina");
    scaena = new ForaminaScaena(this);
    
  }

}
