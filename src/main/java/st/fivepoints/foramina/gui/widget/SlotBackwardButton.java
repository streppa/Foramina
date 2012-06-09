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

import st.fivepoints.foramina.gui.container.SlotContainer;

public class SlotBackwardButton extends SlotButton {

  public SlotBackwardButton( SlotContainer slotContainer ) {
    super(slotContainer, "<==");
  }
  
  public void onButtonClick(ButtonClickEvent event) {
    super.onButtonClick(event, false);
  }
}
