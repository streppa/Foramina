package st.fivepoints.foramina;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.getspout.spoutapi.block.SpoutBlock;

public class ForaminaListener implements Listener {

  public ForaminaListener() {
    Foramina.instance.getServer().getPluginManager().registerEvents(this, Foramina.instance);
  }
  
  @EventHandler()
  public void onBlockPlace( BlockPlaceEvent event ) {
    Foramina.log("onBlockPlace");
    SpoutBlock blockAgainst = (SpoutBlock) event.getBlockAgainst();
    
    if ( blockAgainst.isCustomBlock() && blockAgainst.getCustomBlock() instanceof ForaminaScaena ) {
      event.setBuild(false);
    }
  }
  
}
