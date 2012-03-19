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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.block.SpoutBlock;

import st.fivepoints.foramina.material.Scaena;


public class ScaenaData {
  
  private static List<ScaenaData> scaenus = new ArrayList<ScaenaData>();
  private static int availableSlots = 3;
  
  private List<ForaminaGlyph> glyphs = new ArrayList<ForaminaGlyph>(ScaenaData.availableSlots);
  private Location location;
  private SpoutBlock block;
  
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
  
  public static ScaenaData findScaenaByPlayer( Player player) {
    Location location = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
    return findScaena(location);
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
    this.block = (SpoutBlock) location.getBlock();
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

  public SpoutBlock getBlock() {
    return this.block;
  }
  
  public Scaena getScaena() {
    return (Scaena) this.block.getCustomBlock();
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
