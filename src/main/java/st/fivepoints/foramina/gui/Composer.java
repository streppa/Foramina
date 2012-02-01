package st.fivepoints.foramina.gui;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import st.fivepoints.foramina.Foramina;
import st.fivepoints.foramina.ScaenaData;
import st.fivepoints.foramina.gui.container.SlotContainer;

public class Composer extends GenericPopup {

  public Composer( ScaenaData scaena, SpoutPlayer player ) {
    this.setWidth(216);
    this.setHeight(104);    
    
    GenericGradient background = new GenericGradient();
    background.setPriority(RenderPriority.Highest);
    background.setTopColor(new Color(96, 96, 96, 255));
    background.setBottomColor(new Color(64, 64, 64, 255));
    background.setWidth(this.getWidth());
    background.setHeight(this.getHeight());
    background.shiftXPos(- this.getWidth() / 2); 
    background.shiftYPos(- this.getHeight() / 2); 
    background.setAnchor(WidgetAnchor.CENTER_CENTER);
    
    GenericContainer composer = new GenericContainer();
    composer.setPriority(RenderPriority.High);
    composer.setWidth(this.getWidth());
    composer.setHeight(this.getHeight());
    composer.shiftXPos(- this.getWidth() / 2); 
    composer.shiftYPos(- this.getHeight() / 2); 
    composer.setAnchor(WidgetAnchor.CENTER_CENTER);
    composer.setLayout(ContainerType.HORIZONTAL);
    
    for ( int i = 0; i < scaena.getGlyphs().size(); i++ ) {
      composer.addChild(new SlotContainer(scaena, i, "Cycle"));
    }
    
    this.attachWidgets(Foramina.instance, composer);
  }
  
}

/*
GenericGradient border = new GenericGradient();
border.setTopColor(new Color(94, 94, 92, 253));
border.setBottomColor(new Color(100, 100, 98, 253));
border.setPriority(RenderPriority.High);
border.setWidth(26);
border.setHeight(50);
border.setX(10);
border.setY(10);

GenericGradient background = new GenericGradient( new Color(16, 16, 16, 253) );
background.setPriority(RenderPriority.Normal);
background.setWidth(24);
background.setHeight(48);
background.setX(11);
background.setY(11);

GenericGradient wheel = new GenericGradient( new Color(225, 225, 223, 253) );
wheel.setPriority(RenderPriority.Low);
wheel.setWidth(18);
wheel.setHeight(48);
wheel.setX(14);
wheel.setY(11);

GenericGradient shadow_top = new GenericGradient();
shadow_top.setPriority(RenderPriority.Lowest);
shadow_top.setTopColor(new Color(16, 16, 16, 253));
shadow_top.setBottomColor(new Color(225, 225, 223, 0));
shadow_top.setWidth(18);
shadow_top.setHeight(24);
shadow_top.setX(14);
shadow_top.setY(11);

GenericGradient shadow_bot = new GenericGradient();
shadow_bot.setPriority(RenderPriority.Lowest);
shadow_bot.setTopColor(new Color(225, 225, 223, 0));
shadow_bot.setBottomColor(new Color(16, 16, 16, 253));
shadow_bot.setWidth(18);
shadow_bot.setHeight(24);
shadow_bot.setX(14);
shadow_bot.setY(36);
*/
