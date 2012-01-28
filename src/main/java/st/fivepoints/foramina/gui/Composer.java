package st.fivepoints.foramina.gui;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.gui.container.Composition;
import st.fivepoints.foramina.gui.container.InventoryContainer;


public class Composer extends GenericPopup {

  public Composer( SpoutPlayer player ) {

    /*
    GenericContainer wheelContainer = new GenericContainer();
    wheelContainer.setWidth(26);
    wheelContainer.setHeight(50);
    wheelContainer.setX(10);
    wheelContainer.setY(10);
    
    GenericGradient border = new GenericGradient();
    border.setTopColor(new Color(94, 94, 92, 253));
    border.setBottomColor(new Color(100, 100, 98, 253));
    border.setPriority(RenderPriority.Lowest);
    border.setWidth(26);
    border.setHeight(50);
    border.setX(10);
    border.setY(10);
    
    GenericGradient background = new GenericGradient( new Color(16, 16, 16, 253) );
    border.setPriority(RenderPriority.Low);
    background.setWidth(24);
    background.setHeight(48);
    background.setX(11);
    background.setY(11);
    
    GenericGradient wheel = new GenericGradient( new Color(225, 225, 223, 253) );
    border.setPriority(RenderPriority.Normal);
    wheel.setWidth(18);
    wheel.setHeight(48);
    wheel.setX(14);
    wheel.setY(11);
    
    GenericGradient shadow_top = new GenericGradient();
    border.setPriority(RenderPriority.High);
    shadow_top.setTopColor(new Color(16, 16, 16, 253));
    shadow_top.setBottomColor(new Color(225, 225, 223, 0));
    shadow_top.setWidth(18);
    shadow_top.setHeight(24);
    shadow_top.setX(14);
    shadow_top.setY(11);
    */
    
    GenericGradient shadow_bot = new GenericGradient();
    shadow_bot.setPriority(RenderPriority.Highest);
    shadow_bot.setTopColor(new Color(225, 225, 223, 0));
    shadow_bot.setBottomColor(new Color(16, 16, 16, 253));
    shadow_bot.setWidth(18);
    shadow_bot.setHeight(24);
    shadow_bot.setX(14);
    shadow_bot.setY(36);

    
    this.attachWidgets(Foramina.instance, shadow_bot);
  }
  
}
