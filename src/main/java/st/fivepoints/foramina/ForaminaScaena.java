package st.fivepoints.foramina;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
  
  public void onBlockPlace(World world, int x, int y, int z) { }

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
    
    if ( ! this.inventories.containsKey(location) ) {
      log.info("Created new inventory at " + location.toString());
      this.inventories.put(location, SpoutManager.getInventoryBuilder().construct(9, "Scaena"));
    }
    
    player.openInventoryWindow(this.inventories.get(location));
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

  public void close() {
    ForaminaInventory.save(this.inventories);
  }
}
