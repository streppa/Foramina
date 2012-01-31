package st.fivepoints.foramina.gui.container;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.gui.widget.SlotWidget;

public class SlotWidgetContainer extends GenericContainer {
  
  private List<SlotWidget> slots = new ArrayList<SlotWidget>(16);
  
  private SlotWidget currentSlot;
    
  public SlotWidgetContainer() {
    this.setPriority(RenderPriority.Low);
    
    this.setWidth(64);
    this.setMinWidth(64);
    this.setMaxWidth(64);
    
    this.setHeight(64);
    this.setMinHeight(64);
    this.setMaxHeight(64);
    
    this.setAuto(false);
    //this.setX(0);
    //this.setY(0);
    //this.setAuto(false);
    for ( ItemStack stack : Foramina.getComponants() ) {
      SlotWidget slot = new SlotWidget(stack.clone());
      this.slots.add(slot);
      this.addChild(slot);
    }
    this.currentSlot = slots.get(0);
    //this.setWidth(0).setHeight(0);
  }
  
  public void setCurrent(ItemStack stack) {
    Foramina.log("setCurrent");
    this.currentSlot.setVisible(false);
    SlotWidget newSlot = this.currentSlot;
    for ( SlotWidget slot : this.slots ) {
      Foramina.log(" slot: " + slot.toString() + " idVisible:" + slot.isVisible());
      if ( (slot.getTypeId() == stack.getTypeId()) && (slot.getData() == stack.getData().getData()) ) {
        Foramina.log("  Found a stack to be current.");
        Foramina.log("  stack: " + stack.toString());
        newSlot = slot;
        newSlot.setVisible(true);
        break;
      }
    }
    this.currentSlot = newSlot;
    this.setDirty(true);
    Foramina.log("  slot: " + currentSlot.toString() + " isVisible:" + currentSlot.isVisible());
  }
  
}
