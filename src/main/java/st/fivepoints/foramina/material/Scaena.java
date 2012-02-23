package st.fivepoints.foramina.material;


import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.material.block.GenericCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.ForaminaPlayer;
import st.fivepoints.foramina.ScaenaData;
import st.fivepoints.foramina.gui.Composer;
import st.fivepoints.foramina.task.WatchScaenus;


public class Scaena extends GenericCustomBlock {

  private static final int textureSize = 256;
  private static final int spriteSize  = 16;
  private static final String textureUrl = "http://dl.dropbox.com/u/104908/foramina-texture.png";
  private static final Texture texture = new Texture(Foramina.instance, textureUrl, textureSize, textureSize, spriteSize); 
  
  public Scaena() {
    super(Foramina.instance, "Scaena", true, new ScaenaDesign());
    this.setHardness(MaterialData.cobblestone.getHardness());
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int changedId) { }
  
  public void onBlockPlace(World world, int x, int y, int z) {
    ScaenaData scaena = ScaenaData.findScaena(world, x, y, z);
    if ( scaena == null ) scaena = new ScaenaData(world, x, y, z);
  }

  public void onBlockPlace(World world, int x, int y, int z, LivingEntity living) { }

  public void onBlockDestroyed(World world, int x, int y, int z) { 
    ScaenaData scaena = ScaenaData.findScaena(world, x, y, z);
    if ( scaena != null ) ScaenaData.removeScaena(scaena);
  }

  public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
    
    ScaenaData scaena = ScaenaData.findScaena(world, x, y, z);
    if ( scaena == null ) scaena = new ScaenaData(world, x, y, z);
    
    if ( ! (player.getMainScreen().getActivePopup() instanceof Composer) ) {
      player.getMainScreen().attachPopupScreen(new Composer(scaena, player));
    }
    return true;
  }

  public void onEntityMoveAt(World world, int x, int y, int z, Entity entity) {}

/*  public void onPlayerTreadAt(World world, int x, int y, int z, SpoutPlayer player) {
    
  }
  */
  public void onBlockClicked(World world, int x, int y, int z, SpoutPlayer player) {}

  public boolean isProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
      return false;
  }

  public boolean isIndirectlyProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
      return false;
  }

  public void onTeleport(Location location, SpoutPlayer player) {
    Foramina.log("Scaena.onTeleport()");
    ScaenaData scaena = ScaenaData.findScaena(location);

    Location destination = null;
    
    if ( scaena != null ) {
      for ( ScaenaData targetScaena : ScaenaData.getScaenus() ) {
        if ( scaena.canLinkTo(targetScaena) ) {
          destination = targetScaena.getLocation().clone();
          ForaminaPlayer foraminaPlayer = ForaminaPlayer.findBySpoutPlayer(player);
          foraminaPlayer.setLastDestinationScaena(targetScaena);
          foraminaPlayer.clearTimer();
          foraminaPlayer.startCooldown();
        }
      }
    }
    
    if ( destination != null ) player.teleport(destination.add(0.5, 1, 0.5));

  }

  public void onTeleport(World world, int x, int y, int z, SpoutPlayer player) {
    this.onTeleport( new Location(world, x, y, z), player);
  }
  
  public static int getTextureSize() {
    return textureSize;
  }

  public static int getSpriteSize() {
    return spriteSize;
  }

  public static String getTextureURL() {
    return textureUrl;
  }

  public static Texture getTexture() {
    return texture;
  }


}
