package st.fivepoints.foramina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.gui.SlotContainer;
import st.fivepoints.foramina.gui.SlotWidget;

public class ForaminaInventory extends Location implements Inventory {
  
  private static Map<Location, Inventory> inventories = new HashMap<Location, Inventory>();
  
  private GenericPopup popup;
  private GenericContainer slots;
  private Location location;
  private Inventory inventory;
  private int availableSlots = 3;
  
  private ForaminaInventory(World world, double x, double y, double z) {
    super(world, x, y, z);
  }
  
  public void inventoryPopup(Location location, SpoutPlayer player) {
    this.popup = new GenericPopup();
    this.slots = new SlotContainer();
    for ( int i = 0; i < this.availableSlots; i++ ) {
      this.slots.addChild(new SlotWidget(i));
    }
    
  }
  
  public static boolean hasInventory(Location location) {
    return inventories.containsKey(location);
    
  }
  
  public static Inventory getInventory(Location location) {
    
    
    return inventories.get(location);
  }
  
  public static Map<Location, Inventory> load() {
    Map<Location, Inventory> inventories = new HashMap<Location, Inventory>();
    try {
      ResultSet rs = Foramina.db.query("SELECT * FROM inventories");
      while ( rs.next() ) {
        World world = Bukkit.getServer().getWorld( UUID.fromString(rs.getString("world_uid")) );
        if ( world == null ) continue;
        Location location = new Location(world, rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"));
        SpoutBlock block = (SpoutBlock) world.getBlockAt(location);
        if ( ! (block.isCustomBlock() && block.getBlockType() instanceof ForaminaScaena) ) continue;
        SpoutItemStack stack = new SpoutItemStack(rs.getInt("type_id"), rs.getInt("quantity"), rs.getShort("durability"));
        if ( inventories.containsKey(location) ) {
          Inventory inventory = inventories.get(location);
          inventory.setItem(rs.getInt("slot"), stack);
        } else {
          Inventory inventory = SpoutManager.getInventoryBuilder().construct(9, "Scaena");
          inventory.setItem(rs.getInt("slot"), stack);
          inventories.put(location, inventory);
        }
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return inventories;
  }
  
  public static void save( Map<Location, Inventory> inventories ) {
    //Foramina.db.execute("BEGIN TRANSACTION");
    Foramina.db.execute("DELETE FROM inventories");
    
    for (Map.Entry<Location, Inventory> entry : inventories.entrySet() ) {
      Location location = entry.getKey();
      String world_uid = location.getWorld().getUID().toString();
      ItemStack[] stacks = entry.getValue().getContents();
      double x = location.getX();
      double y = location.getY();
      double z = location.getZ();
      int slot = -1;
      for ( ItemStack stack : stacks ) {
        slot++;
        String sql = "";
        if ( stack == null ) continue;
        sql = "INSERT INTO inventories (world_uid, x, y, z, slot, type_id, quantity, durability) VALUES (\"" + world_uid + "\", " + x + ", " + y + ", " + z + ", " + slot + ", " + stack.getTypeId() + ", " + stack.getAmount() + ", " + stack.getDurability() + ")";
        Foramina.log(sql);
        Foramina.db.execute(sql);
      }
    }
    //Foramina.db.execute("COMMIT");
    
  }

  public HashMap<Integer, ItemStack> addItem(ItemStack... arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  public HashMap<Integer, ? extends ItemStack> all(int arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  public HashMap<Integer, ? extends ItemStack> all(Material arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  public HashMap<Integer, ? extends ItemStack> all(ItemStack arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  public void clear() {
    // TODO Auto-generated method stub
    
  }

  public void clear(int arg0) {
    // TODO Auto-generated method stub
    
  }

  public boolean contains(int arg0) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean contains(Material arg0) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean contains(ItemStack arg0) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean contains(int arg0, int arg1) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean contains(Material arg0, int arg1) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean contains(ItemStack arg0, int arg1) {
    // TODO Auto-generated method stub
    return false;
  }

  public int first(int arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  public int first(Material arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  public int first(ItemStack arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  public int firstEmpty() {
    // TODO Auto-generated method stub
    return 0;
  }

  public ItemStack[] getContents() {
    // TODO Auto-generated method stub
    return null;
  }

  public ItemStack getItem(int arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getSize() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void remove(int arg0) {
    // TODO Auto-generated method stub
    
  }

  public void remove(Material arg0) {
    // TODO Auto-generated method stub
    
  }

  public void remove(ItemStack arg0) {
    // TODO Auto-generated method stub
    
  }

  public HashMap<Integer, ItemStack> removeItem(ItemStack... arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  public void setContents(ItemStack[] arg0) {
    // TODO Auto-generated method stub
    
  }

  public void setItem(int arg0, ItemStack arg1) {
    // TODO Auto-generated method stub
    
  }
}
