package st.fivepoints.foramina.gui.container;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.gui.widget.SlotButton;
import st.fivepoints.foramina.gui.widget.SlotWidget;

public class SlotContainer extends GenericContainer {

  private List<SlotWidget> glyphs = new ArrayList<SlotWidget>(16);
  
  private int currentGlyphIndex = 0;

  private SlotButton slotButton;
  
  public SlotContainer(int startingGlyphIndex, String label) {
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
    
    for ( ItemStack stack : Foramina.getComponants() ) {
      SlotWidget glyph = new SlotWidget(this, stack.clone());
      this.glyphs.add(glyph);
      this.addChild(glyph);
    }
    
    this.currentGlyphIndex = (startingGlyphIndex >= this.glyphs.size()) ? 0 : startingGlyphIndex;
    this.setCurrent(this.currentGlyphIndex);

    this.slotButton = new SlotButton(this, label);
    this.addChild(this.slotButton);
    //this.setWidth(0).setHeight(0);
  }

  private void setCurrent(int glyphIndex) {
    SlotWidget glyphToHide = this.glyphs.get(this.currentGlyphIndex); 
    glyphToHide.setVisible(false);
    
    // TODO: Check for out-of-bounds, or at least try-catch it.
    SlotWidget glyphToShow = this.glyphs.get(glyphIndex);
    glyphToShow.setVisible(true);
    
    this.setDirty(true);
    this.currentGlyphIndex = glyphIndex;
  }
  
  public void cycleGlyphs() {
    int newGlyphIndex = this.currentGlyphIndex + 1;
    if ( newGlyphIndex >= this.glyphs.size() ) newGlyphIndex = 0;
    this.setCurrent(newGlyphIndex);
  }
  
  public List<SlotWidget> getGlyphs() {
    return this.glyphs;
  }
}
