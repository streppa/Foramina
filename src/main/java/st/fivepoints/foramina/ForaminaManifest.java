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
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericItemWidget;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;


public class ForaminaManifest {
  
  private static List<ForaminaManifest> manifests = new ArrayList<ForaminaManifest>();
  private static String label = "Scaena";
  private static int availableSlots = 3;

  public static List<ForaminaManifest> getManifests() {
    return ForaminaManifest.manifests;
  }
  
  public static ForaminaManifest findManifest(Location location) {
    ForaminaManifest manifest = null;
    for ( ForaminaManifest entry : ForaminaManifest.getManifests() ) {
      if ( entry.getLocation().equals(location) ) { manifest = entry; break; }
    }
    return manifest;
  }
  
  public static ForaminaManifest findManifest(World world, double x, double y, double z) {
    Location location = new Location(world, x, y, z);
    return findManifest(location);
  }
  
  public static boolean removeManifest(ForaminaManifest doomedManifest) {
    return ForaminaManifest.getManifests().remove(doomedManifest);
  }
  
  private Map<SpoutPlayer, GenericPopup> manifestPopup = new HashMap<SpoutPlayer, GenericPopup>();
  private Location location;
  private Inventory inventory;

  public ForaminaManifest(Location location) {
    this.location = location;
    this.inventory = SpoutManager.getInventoryBuilder().construct(availableSlots, label);
    ForaminaManifest.getManifests().add(this);
  }
  
  public ForaminaManifest(World world, double x, double y, double z) {
    this( new Location(world, x, y, z) );
  }

  public void showManifest(SpoutPlayer player) {
    GenericPopup popup = new GenericPopup();
    GenericContainer slots = new GenericContainer();
    for ( int i = 0; i < this.getInventory().getSize(); i++ ) {
      ItemStack item = ( this.getInventory().getItem(i) == null ) ? new ItemStack(Material.AIR) : this.getInventory().getItem(i);
      GenericItemWidget slot = new GenericItemWidget(item);
      slot.setHeight(64).setWidth(64).setDepth(64);
      slots.addChild(slot);
    }
    slots.setAnchor(WidgetAnchor.CENTER_CENTER);
    
    this.manifestPopup.put(player, popup);
    
    popup.attachWidget(Foramina.instance, slots);
    player.getMainScreen().attachPopupScreen(popup);
  }
  
  
  public Location getLocation() {
    return this.location;
  }

  public Inventory getInventory() {
    return this.inventory; 
  }

  public boolean canLinkTo(ForaminaManifest manifest) {
    if ( this.equals(manifest) ) return false;
    if ( this.hasEmptySlots() || manifest.hasEmptySlots() ) return false;
    if ( ! this.matches(manifest) ) return false;
    return true;
  }
  
  public boolean hasEmptySlots() {
    for ( ItemStack stack : this.getInventory().getContents() ) {
      if ( stack == null ) return true;
    }
    return false;
  }
  
  public boolean matches(ForaminaManifest manifest) {
    boolean matches = true;
    for ( int i = 0; i < ForaminaManifest.availableSlots; i++ ) {
      matches = ( this.getInventory().getItem(i).equals(manifest.getInventory().getItem(i)) ) ? matches : false;
    }
    return matches;
  }
  
  public static void load() {
    Foramina.log("Loading manifest.");
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
        if ( ! (block.isCustomBlock() && block.getBlockType() instanceof ForaminaScaena) ) continue;

        ForaminaManifest manifest = ForaminaManifest.findManifest(location);
        if ( manifest == null ) manifest = new ForaminaManifest(location);
        
        ResultSet rs_manifests = Foramina.db.query("SELECT * FROM manifests WHERE location_id = " + rs_locations.getInt("id") + " ORDER BY slot ASC");
        while ( rs_manifests.next() ) {
          int   slot       = rs_manifests.getInt("slot");
          int   typeID     = rs_manifests.getInt("type_id");
          int   quantity   = 1;
          short durability = rs_manifests.getShort("durability");
          manifest.getInventory().setItem(slot, new SpoutItemStack(typeID, quantity, durability));
        }
        rs_manifests.close();
      }
      rs_locations.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public static void save() {
    Foramina.log("Saving manifest.");
    //Foramina.db.execute("BEGIN TRANSACTION");
    Foramina.db.execute("DELETE FROM manifests");
    Foramina.db.execute("DELETE FROM locations");
    
    for ( ForaminaManifest manifest : ForaminaManifest.getManifests() ) {
      String world_uid = manifest.getLocation().getWorld().getUID().toString();
      double x = manifest.getLocation().getX();
      double y = manifest.getLocation().getY();
      double z = manifest.getLocation().getZ();
      int location_id = Foramina.db.insertLocation(world_uid, x, y, z);
      int slot = -1;
      for ( ItemStack stack : manifest.getInventory().getContents() ) {
        slot++;
        if ( stack == null ) continue;
        Foramina.db.insertManifest(location_id, slot, stack.getTypeId(), stack.getDurability());
      }
    }
  }

}
