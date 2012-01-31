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

    /*
    this.setMaxHeight(size);
    this.setMaxWidth(size);
    this.setMinHeight(size);
    this.setMinWidth(size);
    */
    //this.setFixed(false);
    //this.setX(0);
    //this.setY(0);
    this.setVisible(false);
    this.setAnchor(WidgetAnchor.CENTER_CENTER);
    this.shiftXPos(- this.getWidth() / 2);
    this.shiftYPos(- this.getHeight() / 2); 
  }


}
