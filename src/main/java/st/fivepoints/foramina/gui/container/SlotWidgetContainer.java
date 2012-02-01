package st.fivepoints.foramina.gui.container;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.gui.widget.SlotWidget;

public class SlotWidgetContainer extends GenericContainer {
  
  private List<SlotWidget> glyphs = new ArrayList<SlotWidget>(16);
  
  private int currentGlyphIndex = 0;

  public SlotWidgetContainer() {
    this(0);
  }
  
  public SlotWidgetContainer( int startingGlyphIndex ) {
    this.setPriority(RenderPriority.Low);
    
    this.setWidth(64);
    this.setMinWidth(64);
    this.setMaxWidth(64);
    
    this.setHeight(64);
    this.setMinHeight(64);
    this.setMaxHeight(64);
    
    for ( ItemStack stack : Foramina.getComponants() ) {
      SlotWidget glyph = new SlotWidget(stack.clone());
      this.glyphs.add(glyph);
      this.addChild(glyph);
    }
    
    this.currentGlyphIndex = (startingGlyphIndex >= this.glyphs.size()) ? 0 : startingGlyphIndex;
    this.setCurrent(this.currentGlyphIndex);
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
