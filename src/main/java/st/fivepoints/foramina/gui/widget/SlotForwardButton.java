package st.fivepoints.foramina.gui.widget;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;

import st.fivepoints.foramina.gui.container.SlotContainer;

public class SlotForwardButton extends SlotButton {

  public SlotForwardButton( SlotContainer slotContainer ) {
    super(slotContainer, "==>");
  }
  
  public void onButtonClick(ButtonClickEvent event) {
    super.onButtonClick(event, true);
  }
}
