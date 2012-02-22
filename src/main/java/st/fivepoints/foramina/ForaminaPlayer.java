package st.fivepoints.foramina;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.getspout.spoutapi.player.SpoutPlayer;


public class ForaminaPlayer {

  private static List<ForaminaPlayer> players = new ArrayList<ForaminaPlayer>();
  
  private SpoutPlayer spoutPlayer;
  private Map<Location, Date> timestamps = new HashMap<Location, Date>();
  
  
  private ForaminaPlayer( SpoutPlayer player ) {
    this.spoutPlayer = player;
  }

  public static ForaminaPlayer create( SpoutPlayer spoutPlayer ) {
    ForaminaPlayer player = new ForaminaPlayer(spoutPlayer);
    players.add(player);
    return player;
  }
  
  public SpoutPlayer getSpoutPlayer() {
    return this.spoutPlayer;
  }
  
  public Date getTimestamp( Location location ) {
    Date date = null;
    if ( timestamps.containsKey(location) ) date = timestamps.get(location);
    return date;
  }
}
