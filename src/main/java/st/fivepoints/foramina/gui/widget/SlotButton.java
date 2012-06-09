/**
 * Foramina
 * Copyright (C) 2012 Scott Treppa <streppa@gmail.com>
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

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.RenderPriority;

import st.fivepoints.foramina.gui.container.SlotContainer;

public class SlotButton extends GenericButton {

  SlotContainer slotContainer;
  
  public SlotButton(SlotContainer slotContainer, String label) {
    super(label);
    this.slotContainer = slotContainer;
    
    this.setPriority(RenderPriority.Lowest);

    this.setHeight(16);
    this.setMaxHeight(16);
    this.setMinHeight(16);

    this.setWidth(32);
    this.setMaxWidth(32);
    this.setMinWidth(32);
    this.setMargin(1);
  }

  public void onButtonClick(ButtonClickEvent event, boolean isForward) {
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
