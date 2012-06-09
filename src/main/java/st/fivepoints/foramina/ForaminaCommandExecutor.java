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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.inventory.SpoutItemStack;

public class ForaminaCommandExecutor implements CommandExecutor {

    public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
        String commandName = command.getName().toLowerCase(); // Only Players

        if  ( ! (sender instanceof Player) ) {
            sender.sendMessage("/" + commandName + " can only be run from in game.");
            return true;
        }

        if  ( commandName.equals("foramina") ) {
            Player player = (Player) sender;

            if ( args.length == 0 ) return false;
            
            if ( args[0].equals("block") ) {
              int amount = ( args.length == 1 ) ? Integer.parseInt(args[1]) : 1;
              player.getInventory().addItem(new SpoutItemStack(Foramina.scaena, amount));              
            } else if ( args[0].equals("exp") ) {
              int amount = ( args.length == 1 ) ? Integer.parseInt(args[1]) : 10;
              player.setExp(player.getExp() + amount);
            }
            
            return true;
        }
        
        return false;
    }
}
