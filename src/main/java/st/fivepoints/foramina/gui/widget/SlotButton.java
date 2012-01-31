package st.fivepoints.foramina.gui.widget;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.gui.container.SlotWidgetContainer;

public class SlotButton extends GenericButton {

  private SlotWidgetContainer slot;
  
  public SlotButton(String label, SlotWidgetContainer slot) {
    super(label);
    this.setPriority(RenderPriority.Low);
    this.slot = slot;

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
    Foramina.log("  slot.getWidth = "  + slot.getWidth());
    Foramina.log("  slot.getHeight = " + slot.getHeight());
  }
}
