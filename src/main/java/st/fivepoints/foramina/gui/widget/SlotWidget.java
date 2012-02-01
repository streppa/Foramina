package st.fivepoints.foramina.gui.widget;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.GenericItemWidget;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;


public class SlotWidget extends GenericItemWidget {

  public SlotWidget(ItemStack stack) {
    super(stack);
    this.setPriority(RenderPriority.Lowest);

    int size = 16;
    this.setHeight(size);
    this.setWidth(size);
    this.setDepth(size);

    this.setMargin(24);
    
    this.setMaxHeight(size);
    this.setMaxWidth(size);
    this.setMinHeight(size);
    this.setMinWidth(size);
    
    this.setFixed(true);
    
    this.setVisible(false);
  }


}
