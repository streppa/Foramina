
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

import org.bukkit.Location;
// import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.CustomBlock;

// import java.util.HashMap;
import java.util.logging.Logger;

public class Foramina extends JavaPlugin {

  public static CustomBlock activatorPad;
  
  private final ForaminaPlayerListener playerListener = new ForaminaPlayerListener(this);

  public Location playerLoc;
  Logger log = Logger.getLogger("Minecraft");


  public void onDisable() {
    log.info("Disabled message here, shown in console on startup");
  }

  public void onEnable() {
    log.info("Enabling Foramina Plugin");

    PluginManager pm = this.getServer().getPluginManager();

    pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, playerListener, Event.Priority.Normal, this);
    pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);

    activatorPad = new ActivatorPad(this);
  }
  
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    String commandName = command.getName().toLowerCase(); // Only Players
    log.info("The command '" + commandName + "' was attempted.");
    if (!(sender instanceof Player)) {
        sender.sendMessage("/" + commandName + " can only be run from in game.");
        return true;
    }

    if (commandName.equals("foramina")) {
        Player player = (Player) sender;

        player.getInventory().addItem(new SpoutItemStack(activatorPad, 1));
        return true;
    }
    return false;
  }
}
