package st.fivepoints.foramina.recipe;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

import st.fivepoints.foramina.Foramina;

public class ScaenaRecipe extends SpoutShapedRecipe {

  public ScaenaRecipe() {
    super(new SpoutItemStack(Foramina.scaena, 2));
    this.shape("   ", "GCG", "OOO");
    this.setIngredient('C', MaterialData.compass);
    this.setIngredient('G', MaterialData.goldIngot);
    this.setIngredient('O', MaterialData.obsidian);
  }

}
