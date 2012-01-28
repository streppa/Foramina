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

import st.fivepoints.foramina.gui.Composer;


public class ForaminaScaena extends GenericCubeCustomBlock {

  public ForaminaScaena() {
    super(Foramina.instance, "Scaena", "http://i.imgur.com/bg8LO.png", 16);
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int changedId) { }
  
  public void onBlockPlace(World world, int x, int y, int z) { }

  public void onBlockPlace(World world, int x, int y, int z, LivingEntity living) { }

  public void onBlockDestroyed(World world, int x, int y, int z) { 
    Foramina.log("onBlockDestroyed");

    ForaminaManifest manifest = ForaminaManifest.findManifest(world, x, y, z);
    
    if ( manifest != null ) {
      for ( ItemStack stack : manifest.getInventory().getContents() ) {
        if ( stack == null ) continue;
        world.dropItem(manifest.getLocation(), stack);
      }
      
      ForaminaManifest.removeManifest(manifest);
    }

  }

  public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
    Foramina.log("onBlockInteract");
    
    if ( player.getMainScreen().getActivePopup() instanceof Composer ) {
      
    } else {
      player.getMainScreen().attachPopupScreen(new Composer(player));
    }
    /*  
    ForaminaManifest manifest = ForaminaManifest.findManifest(world, x, y, z);

    if ( manifest == null ) {
      manifest = new ForaminaManifest(world, x, y, z);
    }
    
    manifest.showManifest(player);
    */
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
    ForaminaManifest manifest = ForaminaManifest.findManifest(location);

    Location destination = null;
    
    if ( manifest != null ) {
      for ( ForaminaManifest targetManifest : ForaminaManifest.getManifests() ) {
        if ( manifest.canLinkTo(targetManifest) ) destination = targetManifest.getLocation().clone();
      }
    }
    
    if ( destination != null ) player.teleport(destination.add(0.5, 1, 0.5));

  }
  
}
