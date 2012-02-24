package st.fivepoints.foramina;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;


public class ForaminaPlayer {

  private static List<ForaminaPlayer> players = new ArrayList<ForaminaPlayer>();
  
  private Player player;
  private Date timer = null;
  private Date cooldown = new Date();
  private ScaenaData lastScaena;
  private ScaenaData lastDestinationScaena;
  
  private ForaminaPlayer( Player player ) {
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
  
  public static ForaminaPlayer findBySpoutPlayer( SpoutPlayer player ) {
    return findByPlayer( (Player) player);
  }
  
  public static void destroy( ForaminaPlayer player ) {
    players.remove(player);
  }
  
  public static void destroy( Player player ) {
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
  
  public Date getTimer() {
    return this.timer;
  }
  
  public int secondsSinceStart() {
    int seconds = 0;
    if ( this.timer != null ) {
      long timeInSeconds  = new Date().getTime() / 1000;
      long timerInSeconds = this.timer.getTime() / 1000;
      seconds = (int) (timeInSeconds - timerInSeconds);
    }
    return seconds;
  }
  
  public void startTimer() {
    this.timer = new Date();
  }
  
  public void clearTimer() {
    this.timer = null;
  }
  
  public boolean timerIsRunning() {
    return ( this.timer == null ) ? false : true;  
  }
  
  public Date getCooldown() {
    return this.cooldown;
  }
  
  public void startCooldown() {
    this.cooldown = new Date();
  }
  
  public boolean isCoolingDown( int teleportCooldown) {
    long cooldownInSeconds = this.cooldown.getTime() / 1000;
    long timeInSeconds = new Date().getTime() / 1000;
    return ( (timeInSeconds - cooldownInSeconds) <= teleportCooldown );
  }

  public ScaenaData getLastScaena() {
    return this.lastScaena;
  }
  
  public ScaenaData setLastScaena( ScaenaData scaena ) {
    ScaenaData oldScaena = this.lastScaena;
    this.lastScaena = scaena;
    return oldScaena;
  }
  
  public void clearLastScaena() {
    this.lastScaena = null;
  }

  public ScaenaData getLastDestinationScaena() {
    return this.lastDestinationScaena;
  }

  public ScaenaData setLastDestinationScaena( ScaenaData scaena ) {
    ScaenaData oldScaena = this.lastDestinationScaena;
    this.lastDestinationScaena = scaena;
    return oldScaena;
  }
  
  public void clearLastDestinationScaena() {
    this.lastDestinationScaena = null;
  }
}
