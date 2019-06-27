package org.jfree.chart.block;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.ui.Size2D;
import org.jfree.util.PublicCloneable;


























































public class BlockContainer
  extends AbstractBlock
  implements Block, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 8199508075695195293L;
  private List blocks;
  private Arrangement arrangement;
  
  public BlockContainer()
  {
    this(new BorderArrangement());
  }
  





  public BlockContainer(Arrangement arrangement)
  {
    if (arrangement == null) {
      throw new IllegalArgumentException("Null 'arrangement' argument.");
    }
    this.arrangement = arrangement;
    blocks = new ArrayList();
  }
  




  public Arrangement getArrangement()
  {
    return arrangement;
  }
  




  public void setArrangement(Arrangement arrangement)
  {
    if (arrangement == null) {
      throw new IllegalArgumentException("Null 'arrangement' argument.");
    }
    this.arrangement = arrangement;
  }
  





  public boolean isEmpty()
  {
    return blocks.isEmpty();
  }
  





  public List getBlocks()
  {
    return Collections.unmodifiableList(blocks);
  }
  




  public void add(Block block)
  {
    add(block, null);
  }
  





  public void add(Block block, Object key)
  {
    blocks.add(block);
    arrangement.add(block, key);
  }
  


  public void clear()
  {
    blocks.clear();
    arrangement.clear();
  }
  








  public Size2D arrange(Graphics2D g2, RectangleConstraint constraint)
  {
    return arrangement.arrange(this, g2, constraint);
  }
  





  public void draw(Graphics2D g2, Rectangle2D area)
  {
    draw(g2, area, null);
  }
  










  public Object draw(Graphics2D g2, Rectangle2D area, Object params)
  {
    EntityBlockParams ebp = null;
    StandardEntityCollection sec = null;
    if ((params instanceof EntityBlockParams)) {
      ebp = (EntityBlockParams)params;
      if (ebp.getGenerateEntities()) {
        sec = new StandardEntityCollection();
      }
    }
    Rectangle2D contentArea = (Rectangle2D)area.clone();
    contentArea = trimMargin(contentArea);
    drawBorder(g2, contentArea);
    contentArea = trimBorder(contentArea);
    contentArea = trimPadding(contentArea);
    Iterator iterator = blocks.iterator();
    while (iterator.hasNext()) {
      Block block = (Block)iterator.next();
      Rectangle2D bounds = block.getBounds();
      Rectangle2D drawArea = new Rectangle2D.Double(bounds.getX() + area.getX(), bounds.getY() + area.getY(), bounds.getWidth(), bounds.getHeight());
      

      Object r = block.draw(g2, drawArea, params);
      if ((sec != null) && 
        ((r instanceof EntityBlockResult))) {
        EntityBlockResult ebr = (EntityBlockResult)r;
        EntityCollection ec = ebr.getEntityCollection();
        sec.addAll(ec);
      }
    }
    
    BlockResult result = null;
    if (sec != null) {
      result = new BlockResult();
      result.setEntityCollection(sec);
    }
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BlockContainer)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    BlockContainer that = (BlockContainer)obj;
    if (!arrangement.equals(arrangement)) {
      return false;
    }
    if (!blocks.equals(blocks)) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    BlockContainer clone = (BlockContainer)super.clone();
    
    return clone;
  }
}
