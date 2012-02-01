package st.fivepoints.foramina.gui.widget;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;

import st.fivepoints.foramina.gui.container.SlotContainer;

public class SlotBackwardButton extends SlotButton {

  public SlotBackwardButton( SlotContainer slotContainer ) {
    super(slotContainer, "<==");
  }
  
  public void onButtonClick(ButtonClickEvent event) {
    super.onButtonClick(event, false);
  }
}
