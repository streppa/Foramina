package st.fivepoints.foramina.material;


import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.ScaenaData;
import st.fivepoints.foramina.ScaenaScheduler;
import st.fivepoints.foramina.gui.Composer;


public class Scaena extends GenericCubeCustomBlock {

  public Scaena() {
    super(Foramina.instance, "Scaena", "http://i.imgur.com/bg8LO.png", 16);
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int changedId) { }
  
  public void onBlockPlace(World world, int x, int y, int z) { }

  public void onBlockPlace(World world, int x, int y, int z, LivingEntity living) { }

  public void onBlockDestroyed(World world, int x, int y, int z) { 
    Foramina.log("onBlockDestroyed");

    ScaenaData scaena = ScaenaData.findScaena(world, x, y, z);
    if ( scaena != null ) ScaenaData.removeScaena(scaena);
  }

  public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
    Foramina.log("onBlockInteract");
    
    ScaenaData scaena = ScaenaData.findScaena(world, x, y, z);
    if ( scaena == null ) scaena = new ScaenaData(world, x, y, z);
    
    if ( ! (player.getMainScreen().getActivePopup() instanceof Composer) ) {
      Foramina.log(" Loading Composer popup.");
      player.getMainScreen().attachPopupScreen(new Composer(scaena, player));
    }
    return true;
  }

  public void onEntityMoveAt(World world, int x, int y, int z, Entity entity) {
    Foramina.log("onEntityMoveAt");
    if ( entity instanceof Player ) {
      Location loc = new Location(world, x, y, z);
      new ScaenaScheduler(SpoutManager.getPlayer((Player) entity), this, loc, 1);
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
    ScaenaData manifest = ScaenaData.findScaena(location);

    Location destination = null;
    
    if ( manifest != null ) {
      for ( ScaenaData targetManifest : ScaenaData.getScaenus() ) {
        if ( manifest.canLinkTo(targetManifest) ) destination = targetManifest.getLocation().clone();
      }
    }
    
    if ( destination != null ) player.teleport(destination.add(0.5, 1, 0.5));

  }
  
}
