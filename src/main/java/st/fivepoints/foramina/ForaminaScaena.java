package st.fivepoints.foramina;


import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;


public class ForaminaScaena extends GenericCubeCustomBlock {

  private Map<Location, Inventory> inventories;
  
  public ForaminaScaena() {
    super(Foramina.instance, "Scaena", "http://i.imgur.com/bg8LO.png", 16);
    this.inventories = ForaminaInventory.load();
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int changedId) { }
  
  public void onBlockPlace(World world, int x, int y, int z) { }

  public void onBlockPlace(World world, int x, int y, int z, LivingEntity living) { }

  public void onBlockDestroyed(World world, int x, int y, int z) { 
    Foramina.log("onBlockDestroyed");
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
    Foramina.log("onBlockInteract");
    Location location = new Location(world, x, y, z);
    Foramina.log("  location: " + location.toString());
    if ( ! this.inventories.containsKey(location) ) {
      Foramina.log("  created new inventory at " + location.toString());
      this.inventories.put(location, SpoutManager.getInventoryBuilder().construct(9, "Scaena"));
    }
    
    player.openInventoryWindow(this.inventories.get(location));
    Foramina.log("  After the ivnentory window open.");
    return true;
  }

  public void onEntityMoveAt(World world, int x, int y, int z, Entity entity) {
    Foramina.log("onEntityMoveAt");
    if ( entity instanceof Player ) {
      Location loc = new Location(world, x, y, z);
      new ForaminaScaenaScheduler(SpoutManager.getPlayer((Player) entity), this, loc, 1);
    }
  }

  public void onBlockClicked(World world, int x, int y, int z, SpoutPlayer player) {
    Foramina.log("onBlockClicked");
  }

  public boolean isProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
      return false;
  }

  public boolean isIndirectlyProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
      return false;
  }

  public void onTeleport(Location location, SpoutPlayer player) {
    Foramina.log("onTeleport");
    Location destination = this.getDestination(location);
    if ( destination == null ) {
      Foramina.log("  Nowhere to go.");
      return;
    }
    player.teleport(destination.add(0.5, 1, 0.5));
  }
  
  private Location getDestination(Location source) {
    Foramina.log("  getDestination");
    Location destination = null;
    if ( ! this.inventories.containsKey(source) ) return null;
    ItemStack[] source_stacks = this.inventories.get(source).getContents();
    Foramina.log("    source stacks: " + source_stacks.toString() + "(" + source_stacks.hashCode() + ")");
    for (Map.Entry<Location, Inventory> entry : inventories.entrySet() ) {
      Location location = entry.getKey();
      Inventory inventory = entry.getValue();
      ItemStack[] destination_stacks = inventory.getContents();
      if ( ! inventory.contains(Material.WOOL)) continue; 
      if ( source.equals(location) ) continue;
      Foramina.log("      dest stacks: " + destination_stacks.toString() + "(" + destination_stacks.hashCode() + ")");
      if ( source_stacks.length != destination_stacks.length ) continue;
      boolean match = true;
      for ( int i = 0; i < source_stacks.length; i++ ) {
        if ( source_stacks[i] == null && destination_stacks[i] == null ) continue;
        if ( source_stacks[i] == null ^  destination_stacks[i] == null ) { match = false; break; }
        if ( ! source_stacks[i].equals(destination_stacks[i]) ) { match = false; break; }
      }
      if ( match ) {
        Foramina.log("Found a match!");
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
