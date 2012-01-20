package st.fivepoints.foramina;


import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.getspout.spoutapi.block.SpoutBlock;


public class ForaminaBlockListener extends BlockListener {

  private Foramina plugin;

  public ForaminaBlockListener(Foramina plugin) {
    this.plugin = plugin;
  }

  public void onBlockPlace( BlockPlaceEvent event ) {
    this.plugin.log.info("onBlockPlace");
    SpoutBlock blockAgainst = (SpoutBlock) event.getBlockAgainst();
    
    if ( blockAgainst.isCustomBlock() && blockAgainst.getCustomBlock() instanceof ForaminaScaena ) {
      event.setBuild(false);
    }
  }
}
