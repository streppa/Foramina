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

    this.setHeight(16);
    this.setMaxHeight(16);
    this.setMinHeight(16);

    this.setWidth(32);
    this.setMaxWidth(32);
    this.setMinWidth(32);
    
    //this.setX(0);
    //this.setY(96);
    //this.setFixed(false);

  }

  public void onButtonClick(ButtonClickEvent event, boolean isForward) {
    Foramina.log("onButtonClick");
    this.getSlotContainer().cycleSlots(isForward);
  }
  
  public void setSlotContainer(SlotContainer slotContainer) {
    this.slotContainer = slotContainer;
    this.setContainer(slotContainer);
  }
  
  public SlotContainer getSlotContainer() {
    return this.slotContainer;
  }
  
}
