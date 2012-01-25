package st.fivepoints.foramina;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ContainerBlock;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;


public class ForaminaScaena extends GenericCubeCustomBlock {

  Logger log = Logger.getLogger("Minecraft");

  private Map<Location, Inventory> inventories;
  
  private Foramina plugin;
  
  public ForaminaScaena( Foramina plugin ) {
    super(plugin, "Scaena", "http://i.imgur.com/bg8LO.png", 16);
    this.plugin = plugin;
    this.inventories = ForaminaInventory.load();
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int changedId) { }
  
  public void onBlockPlace(World world, int x, int y, int z) {
    Location location = new Location(world, x, y, z);
  }

  public void onBlockPlace(World world, int x, int y, int z, LivingEntity living) { }

  public void onBlockDestroyed(World world, int x, int y, int z) { 
    log.info("onBlockDestroyed");
    Location location = new Location(world, x, y, z);
    if ( this.inventories.containsKey(location) ) {
      ItemStack[] stacks = this.inventories.get(location).getContents();
      for ( ItemStack stack : stacks ) {
        if ( stack == null ) continue;
        world.dropItem(location, stack);
      }
      this.inventories.remove(location);
    }
  }

  public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
    log.info("onBlockInteract");
    Location location = new Location(world, x, y, z);
    log.info("  location: " + location.toString());
    if ( ! this.inventories.containsKey(location) ) {
      log.info("  created new inventory at " + location.toString());
      this.inventories.put(location, SpoutManager.getInventoryBuilder().construct(9, "Scaena"));
    }
    
    player.openInventoryWindow(this.inventories.get(location));
    log.info("  After the ivnentory window open.");
    return true;
  }

  public void onEntityMoveAt(World world, int x, int y, int z, Entity entity) {
    log.info("onEntityMoveAt");
    if ( entity instanceof Player ) {
      Location loc = new Location(world, x, y, z);
      new ForaminaScaenaScheduler(this.plugin, SpoutManager.getPlayer((Player) entity), this, loc, 1);
    }
  }

  public void onBlockClicked(World world, int x, int y, int z, SpoutPlayer player) {
    log.info("onBlockClicked");
  }

  public boolean isProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
      return false;
  }

  public boolean isIndirectlyProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
      return false;
  }

  public void onTeleport(Location location, SpoutPlayer player) {
    log.info("onTeleport");
    Location destination = this.getDestination(location);
    if ( destination == null ) {
      log.info("  Nowhere to go.");
      return;
    }
    player.teleport(destination.add(0.5, 1, 0.5));
  }
  
  private Location getDestination(Location source) {
    log.info("  getDestination");
    Location destination = null;
    if ( ! this.inventories.containsKey(source) ) return null;
    ItemStack[] source_stacks = this.inventories.get(source).getContents();
    log.info("    source stacks: " + source_stacks.toString() + "(" + source_stacks.hashCode() + ")");
    for (Map.Entry<Location, Inventory> entry : inventories.entrySet() ) {
      Location location = entry.getKey();
      Inventory inventory = entry.getValue();
      ItemStack[] destination_stacks = inventory.getContents();
      if ( ! inventory.contains(Material.WOOL)) continue; 
      if ( source.equals(location) ) continue;
      log.info("      dest stacks: " + destination_stacks.toString() + "(" + destination_stacks.hashCode() + ")");
      if ( source_stacks.length != destination_stacks.length ) continue;
      boolean match = true;
      for ( int i = 0; i < source_stacks.length; i++ ) {
        if ( source_stacks[i] == null && destination_stacks[i] == null ) continue;
        if ( source_stacks[i] == null ^  destination_stacks[i] == null ) { match = false; break; }
        if ( ! source_stacks[i].equals(destination_stacks[i]) ) { match = false; break; }
      }
      if ( match ) {
        log.info("Found a match!");
        destination = location.clone();
        break;
      }
    }
    return destination;
  }
  
  public void close() {
    ForaminaInventory.save(this.inventories);
  }
}
