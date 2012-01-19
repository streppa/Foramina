package st.fivepoints.foramina;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.getspout.spoutapi.block.SpoutBlock;


public class ForaminaBlockListener extends BlockListener {

  @SuppressWarnings("unused")
  private Foramina plugin;

  public ForaminaBlockListener(Foramina plugin) {
    this.plugin = plugin;
  }
  
  public void onBlockPlace( BlockPlaceEvent event ) {
    SpoutBlock sblock  = (SpoutBlock) event.getBlockAgainst();
    this.plugin.log.info("Trying to place a block.");
    if ( sblock.isCustomBlock() && sblock.getCustomBlock() instanceof ForaminaScaena ) {
      this.plugin.log.info("Trying to place a block on a Scaena block.");
      event.setCancelled(true);
    }
  }
}
