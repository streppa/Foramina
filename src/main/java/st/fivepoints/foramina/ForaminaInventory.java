package st.fivepoints.foramina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.Spout;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.SpoutServer;
import org.getspout.spoutapi.SpoutWorld;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.inventory.SpoutItemStack;

public class ForaminaInventory {
  
  static Logger log = Logger.getLogger("Minecraft");
  
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
        log.info(sql);
        Foramina.db.execute(sql);
      }
    }
    //Foramina.db.execute("COMMIT");
    
  }
}
