package st.fivepoints.foramina;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ForaminaScaenaActivate extends Event implements Cancellable {

  private static final long serialVersionUID = 8197880108864898252L;
  private SpoutPlayer splayer;
  private SpoutBlock  scaena;
  
  private boolean cancelled;
  
  protected ForaminaScaenaActivate( String event_name, SpoutPlayer splayer, SpoutBlock sblock ) {
    super(event_name);
    this.splayer = splayer;
    this.scaena  = sblock;
  }

  public SpoutPlayer getSpoutPlayer() {
    return this.splayer;
  }
  
  public void setSpoutPlayer( SpoutPlayer splayer ) {
    this.splayer = splayer;
  }
  
  public SpoutBlock getScaena() {
    return this.scaena;
  }
  
  public void setScaena( SpoutBlock sblock ) {
    this.scaena = sblock;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }

  public void setCancelled( boolean arg0 ) {
    this.cancelled = arg0;
  }

}
