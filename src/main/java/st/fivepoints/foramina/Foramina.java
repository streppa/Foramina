package st.fivepoints.foramina;

/*
 This file is part of Foramina

 Foobar is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Foobar is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import st.fivepoints.foramina.material.Scaena;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Foramina extends JavaPlugin {

  public static Foramina instance;
  public static String label = "[Foramina]";
  public static Logger log = Logger.getLogger("Foramina");

  public static Scaena scaena;
  public static ScaenaScheduler foraminaScaenaScheduler;
  public static ForaminaPersistence db;
  
  public static List<ForaminaGlyph> availableGlyphs = initializeGlyphs();
  
  private final ForaminaCommandExecutor commandExecutor = new ForaminaCommandExecutor();
  
  @SuppressWarnings("unused")
  private ForaminaListener listener;

  public Foramina() {
    Foramina.instance = this;
  }
  
  public static void log(String msg) {
    log.info(label + " " + msg);
  }
  
  public void onDisable() {
    ScaenaData.save();
    Foramina.db.disconnect();
    log("Plugin disabled.");
  }

  public void onEnable() {
    log("Plugin enabled.");
    scaena = new Scaena();
    Foramina.db = new ForaminaPersistence("foramina");
    ScaenaData.load();
    
    getCommand("foramina").setExecutor(commandExecutor);
    
    this.listener = new ForaminaListener();

    // foraminaScaenaScheduler = new ForaminaScaenaScheduler(this);
    
    this.getConfig().options().copyDefaults(true);
    saveConfig();
    
    
  }

  private static List<ForaminaGlyph> initializeGlyphs() {
    List<ForaminaGlyph> glyphs = new ArrayList<ForaminaGlyph>(16);
    for ( byte i = 0; i < 16; i++ ) {
      ItemStack stack = new MaterialData(Material.WOOL, i).toItemStack();
      ForaminaGlyph glyph = new ForaminaGlyph(i, stack);
      glyphs.add(glyph);
    }
    return glyphs;
  }
  
  public static List<ForaminaGlyph> getAvailableGlyphs() {
    return availableGlyphs;
  }
}
