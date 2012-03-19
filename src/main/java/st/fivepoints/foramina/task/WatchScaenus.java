// $Id$
/*
 * Foramina
 * Copyright (C) 2011 Scott Treppa [lose_the_grimm]
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

package st.fivepoints.foramina.task;

import java.util.List;

import org.bukkit.Bukkit;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.ForaminaPlayer;
import st.fivepoints.foramina.ScaenaData;

public class WatchScaenus implements Runnable {

  // TODO: Make 'defaultInterval' configurable;
  private static int defaultInterval = 3;
  
  private int intervalInSeconds;
  private int intervalInTicks;
  
  public WatchScaenus( int intervalInSeconds ) {
    
    this.intervalInSeconds = intervalInSeconds;
    this.intervalInTicks   = this.intervalInSeconds * 20;
    
    Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Foramina.instance, this, 0, this.intervalInTicks);
  }
  
  public WatchScaenus() {
    this( defaultInterval );
  }
  
  public void run() {
    
    List<ForaminaPlayer> players = ForaminaPlayer.getPlayers();
   
    for ( ForaminaPlayer player : players ) {
      SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player.getPlayer());
      ScaenaData scaena       = ScaenaData.findScaenaByPlayer(player.getPlayer());
      
      if ( scaena == null ) continue;
      
      SpoutBlock block = scaena.getBlock();
     // scaena.getScaena().onPlayerTreadAt(block.getWorld(), block.getX(), block.getY(), block.getZ(), spoutPlayer);
     }
    
  }

}
