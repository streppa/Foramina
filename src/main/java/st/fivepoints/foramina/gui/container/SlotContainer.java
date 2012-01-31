package st.fivepoints.foramina.gui.container;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.gui.widget.SlotButton;

public class SlotContainer extends GenericContainer {

  private SlotWidgetContainer slotWidgetContainer;
  private SlotButton slotButton;
  
  public SlotContainer(ItemStack stack, String label) {
    this.setPriority(RenderPriority.Normal);
    
    this.setWidth(64);
    this.setMinWidth(64);
    this.setMaxWidth(64);
    
    this.setHeight(96);
    this.setMinHeight(96);
    this.setMaxHeight(96);
    
    this.setMargin(4);
    this.setLayout(ContainerType.VERTICAL);
    //this.setAuto(false);
    
    this.slotWidgetContainer = new SlotWidgetContainer();
    this.slotWidgetContainer.setCurrent(stack);
    this.slotButton = new SlotButton(label, slotWidgetContainer);
    this.addChildren(this.slotWidgetContainer, this.slotButton);
    //this.setWidth(0).setHeight(0);
  }
  
}
