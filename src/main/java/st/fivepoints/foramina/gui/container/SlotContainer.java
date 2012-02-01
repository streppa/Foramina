package st.fivepoints.foramina.gui.container;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.ForaminaGlyph;
import st.fivepoints.foramina.ScaenaData;
import st.fivepoints.foramina.gui.widget.SlotBackwardButton;
import st.fivepoints.foramina.gui.widget.SlotButton;
import st.fivepoints.foramina.gui.widget.SlotForwardButton;
import st.fivepoints.foramina.gui.widget.SlotWidget;

public class SlotContainer extends GenericContainer {

  private List<SlotWidget> slots = new ArrayList<SlotWidget>();
  
  private int currentSlotIndex = 0;
  private ScaenaData scaena;
  private int scaenaSlotIndex;
  
  private GenericContainer   slotButtons;
  private SlotBackwardButton slotBackward;
  private SlotForwardButton  slotForward;
  
  public SlotContainer(ScaenaData scaena, int scaenaSlotIndex, String label) {
    this.scaena = scaena;
    this.scaenaSlotIndex = scaenaSlotIndex;
    
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
    
    for ( ForaminaGlyph glyph : Foramina.getAvailableGlyphs() ) {
      SlotWidget slot = new SlotWidget(this, glyph);
      this.slots.add(glyph.getId(), slot);
      this.addChild(slot);
    }
    
    
    this.currentSlotIndex = this.scaena.getGlyphs().get(this.scaenaSlotIndex).getId();
    this.setCurrent(this.currentSlotIndex);

    this.slotBackward = new SlotBackwardButton(this);
    this.slotForward  = new SlotForwardButton(this);

    this.slotButtons = new GenericContainer();
    this.slotButtons.setLayout(ContainerType.HORIZONTAL);
    this.slotButtons.addChildren(this.slotBackward, this.slotForward);
    
    this.addChild(this.slotButtons);
  }

  private void setCurrent(int slotIndex) {
    SlotWidget slotToHide = this.slots.get(this.currentSlotIndex); 
    slotToHide.setVisible(false);
    
    // TODO: Check for out-of-bounds, or at least try-catch it.
    SlotWidget slotToShow = this.slots.get(slotIndex);
    slotToShow.setVisible(true);
    slotToShow.setFixed(false);
    
    this.setDirty(true);
    this.currentSlotIndex = slotIndex;
    
    this.scaena.getGlyphs().set(this.scaenaSlotIndex, slotToShow.getGlyph());
  }
  
  public void cycleSlots(boolean isForward) {
    int newSlotIndex = this.currentSlotIndex;
    if ( isForward ) {
      newSlotIndex = ( ++newSlotIndex >= this.slots.size() ) ? 0 : newSlotIndex;
    } else {
      newSlotIndex = ( --newSlotIndex < 0 ) ? this.slots.size() - 1 : newSlotIndex;
    }
    this.setCurrent(newSlotIndex);
  }
  
  public List<SlotWidget> getSlots() {
    return this.slots;
  }
}
