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
