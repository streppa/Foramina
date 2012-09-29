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

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ScaenaScheduler implements Runnable {

  private int teleportDelay;
  private int teleportCooldown;
  
  public ScaenaScheduler( int teleportDelay, int teleportCooldown ) {
    this.teleportDelay    = teleportDelay;
    this.teleportCooldown = teleportCooldown;
    Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Foramina.instance, this, 0, 10);
  }
  
  public void run() {
    List<ForaminaPlayer> players = ForaminaPlayer.getPlayers();

    for ( ForaminaPlayer player : players ) {
      SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player.getPlayer());
      ScaenaData scaena = ScaenaData.findScaenaByPlayerLocation(player.getPlayer());
      
      if ( scaena == null ) {
        player.clearLastScaena();
        player.clearLastDestinationScaena();
        continue;
      }
      
      if ( player.getLastDestinationScaena() != null && scaena.equals(player.getLastDestinationScaena()) ) continue;
      
      if ( player.isCoolingDown(this.teleportCooldown) ) continue;

      if ( ! scaena.equals(player.getLastScaena()) ) player.startTimer();
      
      Location location = scaena.getLocation();
      if ( player.timerIsRunning() ) {
        if ( player.secondsSinceStart() > this.teleportDelay ) player.startTimer();
        
        if ( player.secondsSinceStart() == this.teleportDelay ) {
          scaena.getScaena().onTeleport(location, spoutPlayer);
        }
      } else {
        player.startTimer();
      }

      player.setLastScaena(scaena);
     }
    
  }

}
