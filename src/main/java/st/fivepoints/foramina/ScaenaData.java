package st.fivepoints.foramina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.getspout.spoutapi.block.SpoutBlock;

import st.fivepoints.foramina.material.Scaena;


public class ScaenaData {
  
  private static List<ScaenaData> scaenus = new ArrayList<ScaenaData>();
  private static int availableSlots = 3;
  
  private List<ForaminaGlyph> glyphs = new ArrayList<ForaminaGlyph>(ScaenaData.availableSlots);
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
  
  public static ScaenaData findScaenaByOverheadLocation( Location location ) {
    return findScaena(location.clone().add(0, 0, -1));
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
      this.glyphs.add(Foramina.getAvailableGlyphs().get(0));
    }
    ScaenaData.getScaenus().add(this);
  }
  
  public ScaenaData(World world, double x, double y, double z) {
    this( new Location(world, x, y, z) );
  }  
  
  public Location getLocation() {
    return this.location;
  }

  public List<ForaminaGlyph> getGlyphs() {
    return this.glyphs; 
  }

  public boolean canLinkTo(ScaenaData scaena) {
    if ( this.equals(scaena) ) return false;
    if ( ! this.matches(scaena) ) return false;
    return true;
  }
  
  public boolean matches(ScaenaData scaena) {
    boolean matches = true;
    for ( int i = 0; i < ScaenaData.availableSlots; i++ ) {
      matches = ( this.getGlyphs().get(i).equals(scaena.getGlyphs().get(i)) ) ? matches : false;
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
        
        ScaenaData scaena = ScaenaData.findScaena(location);
        if ( scaena == null ) scaena = new ScaenaData(location);
        
        ResultSet rs_scaena = Foramina.db.query("SELECT * FROM scaenus WHERE location_id = " + rs_locations.getInt("id") + " ORDER BY slot ASC");
        while ( rs_scaena.next() ) {
          int slot       = rs_scaena.getInt("slot");
          int glyphIndex = rs_scaena.getInt("glyph_index");
          
          glyphIndex = ( glyphIndex >= Foramina.getAvailableGlyphs().size() ) ? 0 : glyphIndex;
          
          scaena.getGlyphs().set(slot, Foramina.getAvailableGlyphs().get(glyphIndex));
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
        Foramina.db.insertScaena(location_id, slot, scaena.getGlyphs().get(slot).getId());
      }
    }
  }

}
