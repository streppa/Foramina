package st.fivepoints.foramina.gui.container;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.WidgetAnchor;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.gui.widget.SlotWidget;

public class InventoryContainer extends GenericContainer {

  public InventoryContainer( ItemStack[] stacks ) {
    this.setAnchor(WidgetAnchor.CENTER_CENTER);
    this.setLayout(ContainerType.HORIZONTAL);
    //this.setAuto(false);
    //this.setFixed(false);
    
    this.setHeight(56);
    this.setWidth(164);
    

    Foramina.log("Adding stacks to Inventory Container Widget.");
    for ( ItemStack stack : stacks ) {
      if ( stack == null ) {
        this.addChild( new SlotContainer());
      } else {
        this.addChild( new SlotContainer(stack));
      }
    }
  }
  
}
