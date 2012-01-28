package st.fivepoints.foramina.gui.container;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.GenericContainer;


import st.fivepoints.foramina.gui.widget.SlotWidget;


public class Composition extends GenericContainer {

  public Composition() {
    SlotWidget slot1 = new SlotWidget(new ItemStack(Material.WOOL));
    slot1.setFixed(true);
    slot1.setMargin(10);
    slot1.setHeight(8).setWidth(8).setDepth(8);

    this.addChildren(slot1);
  }
  
}
