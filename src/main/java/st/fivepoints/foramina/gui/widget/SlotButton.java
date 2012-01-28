package st.fivepoints.foramina.gui.widget;

import org.getspout.spoutapi.gui.GenericButton;

public class SlotButton extends GenericButton {

  private SlotWidget slot;
  
  public SlotButton(String label, SlotWidget slot) {
    super(label);
    this.slot = slot;
    this.setHeight(18);
    this.setWidth(34);
  }
  
  public SlotWidget getSlot() {
    return this.slot;
  }
  
}
