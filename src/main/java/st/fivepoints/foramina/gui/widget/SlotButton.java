package st.fivepoints.foramina.gui.widget;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.gui.container.SlotContainer;

public class SlotButton extends GenericButton {

  SlotContainer slotContainer;
  
  public SlotButton(SlotContainer slotContainer, String label) {
    super(label);
    this.slotContainer = slotContainer;
    
    this.setPriority(RenderPriority.Low);

    this.setHeight(32);
    this.setMaxHeight(32);
    this.setMinHeight(32);

    this.setWidth(64);
    this.setMaxWidth(64);
    this.setMinWidth(32);
    
    //this.setX(0);
    //this.setY(96);
    //this.setFixed(false);

  }

  public void onButtonClick(ButtonClickEvent event) {
    Foramina.log("onButtonClick");
    this.getSlotContainer().cycleGlyphs();
  }
  
  public void setSlotContainer(SlotContainer slotContainer) {
    this.slotContainer = slotContainer;
    this.setContainer(slotContainer);
  }
  
  public SlotContainer getSlotContainer() {
    return this.slotContainer;
  }
  
}
