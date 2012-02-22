package st.fivepoints.foramina;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class ForaminaPlayer {

  private static List<ForaminaPlayer> players = new ArrayList<ForaminaPlayer>();
  
  private Player player;
  private Map<Location, Date> timestamps = new HashMap<Location, Date>();
  private Date cooldown = new Date();
  
  private ForaminaPlayer( Player player ) {
    Foramina.log("Creating a ForaminaPlayer object for '" + player.getDisplayName() + "'.");
    this.player = player;
  }

  public static ForaminaPlayer create( Player player ) {
    ForaminaPlayer foraminaPlayer = new ForaminaPlayer(player);
    players.add(foraminaPlayer);
    return foraminaPlayer;
  }
  
  public static ForaminaPlayer findByPlayer( Player craftPlayer ) {
    ForaminaPlayer foraminaPlayer = null;
    
    for ( ForaminaPlayer player : players ) {
      if ( player.getPlayer().equals(craftPlayer) ) {
        foraminaPlayer = player;
        break;
      }
    }
    // TODO: If this is null it should throw an exception. Any player logging in should have had a ForaminaPlayer object created.
    return foraminaPlayer;
  }
  
  public static void destroy( ForaminaPlayer player ) {
    players.remove(player);
  }
  
  public static void destroy( Player player ) {
    Foramina.log("About to remove ForaminaPlayer");
    ForaminaPlayer foraminaPlayer = findByPlayer(player);
    if ( foraminaPlayer == null ) return;
    players.remove(foraminaPlayer);
  }

  public static List<ForaminaPlayer> getPlayers() {
    return players;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public Date getTimestamp( Location location ) {
    Date date = null;
    if ( timestamps.containsKey(location) ) date = timestamps.get(location);
    return date;
  }
  
  public void addTimestamp( Location location ) {
    timestamps.put(location, new Date());
    
  }
  
  public Date getCooldown() {
    return this.cooldown;
  }
  
  public void restartCooldown() {
    this.cooldown = new Date();
  }
  
  public boolean cooldownIsFinished() {
    Foramina.log(this.player.getDisplayName() + " is cooling down.");
    long cooldownInSeconds = this.cooldown.getTime() / 1000;
    long timeInSeconds = new Date().getTime() / 1000;
    
    return ( (timeInSeconds - cooldownInSeconds) >= Foramina.teleportCooldown );
  }
  
  public boolean hasTimestamp( Location location ) {
    return ( this.getTimestamp(location) != null ) ? true : false; 
  }
}
