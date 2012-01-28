package st.fivepoints.foramina.gui.container;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.GenericContainer;

import st.fivepoints.foramina.gui.widget.SlotWidget;

public class SlotContainer extends GenericContainer {

  public SlotContainer() {
    this.setHeight(16);
    this.setWidth(16);
    this.setMargin(2);
    this.setAuto(true);
    this.setFixed(true);
  }
  
  public SlotContainer(ItemStack stack) {
    this();
    this.addChild(new SlotWidget(stack));
  }
  
}
