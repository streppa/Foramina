// $Id$
/*
 * Foramina
 * Copyright (C) 2011 Scott Treppa [lose_the_grimm]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

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
    
    this.setPriority(RenderPriority.Normal);

    int size = 16;
    this.setHeight(size);
    this.setWidth(size);
    this.setDepth(size);

    this.setMinHeight(size);
    this.setMinWidth(size);

    this.setMaxHeight(size);
    this.setMaxWidth(size);
    
    this.setMargin(15, 23, 20, 23);

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
