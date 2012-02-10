package st.fivepoints.foramina.material;

import org.getspout.spoutapi.block.design.GenericBlockDesign;
import org.getspout.spoutapi.block.design.Quad;
import org.getspout.spoutapi.block.design.Texture;

import st.fivepoints.foramina.Foramina;

public class ScaenaDesign extends GenericBlockDesign {

  public ScaenaDesign() {
    Texture texture = Scaena.getTexture();
    
    setBoundingBox(0, 0, 0, 1, 1, 1).setQuadNumber(6);
    setTexture(Foramina.instance, texture.getTexture()).setMinBrightness(1F).setMaxBrightness(1F);
    
    Quad top = new Quad(0, texture.getSubTexture(2));
    top.addVertex(0,  1F,  1F,  1F);
    top.addVertex(1, 0F,  1F,  1F);
    top.addVertex(2, 0F,  1F, 0F);
    top.addVertex(3,  1F,  1F, 0F);
    
    Quad side0 = new Quad(1, texture.getSubTexture(1));
    side0.addVertex(0,  1F,  1F, 0F);
    side0.addVertex(1, 0F,  1F, 0F);
    side0.addVertex(2, 0F, 0F, 0F);
    side0.addVertex(3,  1F, 0F, 0F);
    
    Quad side1 = new Quad(2, texture.getSubTexture(1));
    side1.addVertex(0, 0F,  1F, 0F);
    side1.addVertex(1, 0F,  1F,  1F);
    side1.addVertex(2, 0F, 0F,  1F);
    side1.addVertex(3, 0F, 0F, 0F);
    
    Quad side2 = new Quad(3, texture.getSubTexture(1));
    side2.addVertex(0, 0F,  1F,  1F);
    side2.addVertex(1,  1F,  1F,  1F);
    side2.addVertex(2,  1F, 0F,  1F);
    side2.addVertex(3, 0F, 0F,  1F);
    
    Quad side3 = new Quad(4, texture.getSubTexture(1));
    side3.addVertex(0,  1F,  1F,  1F);
    side3.addVertex(1,  1F,  1F, 0F);
    side3.addVertex(2,  1F, 0F, 0F);
    side3.addVertex(3,  1F, 0F,  1F);
    
    Quad bottom = new Quad(5, texture.getSubTexture(0));
    bottom.addVertex(0, 0F, 0F,  1F);
    bottom.addVertex(1,  1F, 0F,  1F);
    bottom.addVertex(2, 0F, 0F,  1F);
    bottom.addVertex(3, 0F, 0F, 0F);
    
    this.setQuad(top).setQuad(side0).setQuad(side1).setQuad(side2).setQuad(side3).setQuad(bottom);
  }
  
}
