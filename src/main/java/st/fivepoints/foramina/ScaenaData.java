package st.fivepoints.foramina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericItemWidget;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.material.Scaena;


public class ScaenaData {
  
  private static List<ScaenaData> scaenus = new ArrayList<ScaenaData>();
  private static int availableSlots = 3;
  
  private List<ItemStack> componants = new ArrayList<ItemStack>(ScaenaData.availableSlots);
  private Location location;
  
  public static List<ScaenaData> getScaenus() {
    return ScaenaData.scaenus;
  }
  
  public static ScaenaData findScaena(Location location) {
    ScaenaData scaena = null;
    for ( ScaenaData entry : ScaenaData.getScaenus() ) {
      if ( entry.getLocation().equals(location) ) { scaena = entry; break; }
    }
    return scaena;
  }
  
  public static ScaenaData findScaena(World world, double x, double y, double z) {
    Location location = new Location(world, x, y, z);
    return findScaena(location);
  }
  
  public static boolean removeScaena(ScaenaData doomedScaenus) {
    return ScaenaData.getScaenus().remove(doomedScaenus);
  }

  public ScaenaData(Location location) {
    this.location = location;
    for ( int i = 0; i < ScaenaData.availableSlots; i++ ) {
      this.componants.add(new MaterialData(Material.WOOL, (byte) 0).toItemStack());
    }
    ScaenaData.getScaenus().add(this);
  }
  
  public ScaenaData(World world, double x, double y, double z) {
    this( new Location(world, x, y, z) );
  }  
  
  public Location getLocation() {
    return this.location;
  }

  public List<ItemStack> getComponants() {
    return this.componants; 
  }

  public boolean canLinkTo(ScaenaData scaena) {
    if ( this.equals(scaena) ) return false;
    if ( this.hasEmptySlots() || scaena.hasEmptySlots() ) return false;
    if ( ! this.matches(scaena) ) return false;
    return true;
  }
  
  public boolean hasEmptySlots() {
    for ( ItemStack stack : this.getComponants() ) {
      if ( stack == null ) return true;
    }
    return false;
  }
  
  public boolean matches(ScaenaData scaena) {
    boolean matches = true;
    for ( int i = 0; i < ScaenaData.availableSlots; i++ ) {
      matches = ( this.getComponants().get(i).equals(scaena.getComponants().get(i)) ) ? matches : false;
    }
    return matches;
  }
  
  public static void load() {
    Foramina.log("Loading scaenus.");
    try {
      ResultSet rs_locations = Foramina.db.query("SELECT * FROM locations");
      while ( rs_locations.next() ) {
        
        World world = Bukkit.getServer().getWorld( UUID.fromString(rs_locations.getString("world_uid")) );
        if ( world == null ) continue;
        double x = rs_locations.getDouble("x");
        double y = rs_locations.getDouble("y");
        double z = rs_locations.getDouble("z");
        Location location = new Location(world, x, y, z);
        
        SpoutBlock block = (SpoutBlock) world.getBlockAt(location);
        if ( ! (block.isCustomBlock() && block.getBlockType() instanceof Scaena) ) continue;

        ScaenaData manifest = ScaenaData.findScaena(location);
        if ( manifest == null ) manifest = new ScaenaData(location);
        
        ResultSet rs_scaena = Foramina.db.query("SELECT * FROM manifests WHERE location_id = " + rs_locations.getInt("id") + " ORDER BY slot ASC");
        while ( rs_scaena.next() ) {
          int   slot       = rs_scaena.getInt("slot");
          int   typeID     = rs_scaena.getInt("type_id");
          int   quantity   = 1;
          short durability = 1;
          byte  data       = rs_scaena.getByte("data");
          manifest.getComponants().add(slot, new ItemStack(typeID, quantity, durability, data));
        }
        rs_scaena.close();
      }
      rs_locations.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public static void save() {
    Foramina.log("Saving manifest.");
    Foramina.db.execute("DELETE FROM scaenus");
    Foramina.db.execute("DELETE FROM locations");
    
    for ( ScaenaData scaena : ScaenaData.getScaenus() ) {
      String world_uid = scaena.getLocation().getWorld().getUID().toString();
      double x = scaena.getLocation().getX();
      double y = scaena.getLocation().getY();
      double z = scaena.getLocation().getZ();
      int location_id = Foramina.db.insertLocation(world_uid, x, y, z);
      for ( int slot = 0; slot < ScaenaData.availableSlots; slot++ ) {
        ItemStack stack = scaena.getComponants().get(slot);
        if ( stack == null ) continue;
        Foramina.db.insertScaena(location_id, slot, stack.getTypeId(), stack.getData().getData());
      }
    }
  }

}
