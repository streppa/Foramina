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

package st.fivepoints.foramina;

import org.bukkit.inventory.ItemStack;

public class ForaminaGlyph {

  private int id;
  private ItemStack stack;
  
  public ForaminaGlyph(int id, ItemStack stack) {
    this.id = id;
    this.stack = stack;
  }

  public int getId() {
    return this.id;
  }
  
  public ItemStack getStack() {
    return this.stack;
  }
  
}
