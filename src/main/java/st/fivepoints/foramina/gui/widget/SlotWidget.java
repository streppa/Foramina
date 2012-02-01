package st.fivepoints.foramina.gui.widget;

import org.getspout.spoutapi.gui.GenericItemWidget;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.ForaminaGlyph;
import st.fivepoints.foramina.gui.container.SlotContainer;

public class SlotWidget extends GenericItemWidget {

  private SlotContainer slotContainer;
  private ForaminaGlyph glyph;
  
  public SlotWidget(SlotContainer slotContainer, ForaminaGlyph glyph) {
    super(glyph.getStack());
    this.slotContainer = slotContainer;
    this.glyph = glyph;
    
    this.setPriority(RenderPriority.Lowest);

    int size = 16;
    this.setHeight(size);
    this.setWidth(size);
    this.setDepth(size);

    this.setMargin(24);
    this.setMarginBottom(40);
    
    this.setMaxHeight(size);
    this.setMaxWidth(size);
    this.setMinHeight(size);
    this.setMinWidth(size);
    
    this.setFixed(true);
    
    this.setVisible(false);
  }

  public void setSlotContainer(SlotContainer slotContainer) {
    this.slotContainer = slotContainer;
    this.setContainer(slotContainer);
  }
  
  public SlotContainer getSlotContainer() {
    return this.slotContainer;
  }

  public ForaminaGlyph getGlyph() {
    return this.glyph;
  }
  
}
