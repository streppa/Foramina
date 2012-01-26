package st.fivepoints.foramina.gui;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.GenericItemWidget;

public class SlotWidget extends GenericItemWidget {

  private int slot;
  
  public SlotWidget() {
    super();  
  }
  
  public SlotWidget(ItemStack item) {
    super(item);
  }
  
  public SlotWidget(int slot) {
    super();
    this.slot = slot;
  }
  
  public SlotWidget(int slot, ItemStack item) {
    super(item);
    this.slot = slot;
  }
  
  public SlotWidget setSlot(int slot) {
    this.slot = slot;
    return this;
  }
  
  public int getSlot() {
    return this.slot;
  }
}
