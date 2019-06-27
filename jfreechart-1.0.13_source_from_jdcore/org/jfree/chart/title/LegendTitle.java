package org.jfree.chart.title;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.block.Arrangement;
import org.jfree.chart.block.Block;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.block.BlockResult;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.CenterArrangement;
import org.jfree.chart.block.ColumnArrangement;
import org.jfree.chart.block.EntityBlockParams;
import org.jfree.chart.block.FlowArrangement;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.entity.TitleEntity;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;







































































public class LegendTitle
  extends Title
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 2644010518533854633L;
  public static final Font DEFAULT_ITEM_FONT = new Font("SansSerif", 0, 12);
  


  public static final Paint DEFAULT_ITEM_PAINT = Color.black;
  


  private LegendItemSource[] sources;
  


  private transient Paint backgroundPaint;
  


  private RectangleEdge legendItemGraphicEdge;
  


  private RectangleAnchor legendItemGraphicAnchor;
  


  private RectangleAnchor legendItemGraphicLocation;
  


  private RectangleInsets legendItemGraphicPadding;
  


  private Font itemFont;
  


  private transient Paint itemPaint;
  


  private RectangleInsets itemLabelPadding;
  


  private BlockContainer items;
  


  private Arrangement hLayout;
  


  private Arrangement vLayout;
  


  private BlockContainer wrapper;
  


  public LegendTitle(LegendItemSource source)
  {
    this(source, new FlowArrangement(), new ColumnArrangement());
  }
  









  public LegendTitle(LegendItemSource source, Arrangement hLayout, Arrangement vLayout)
  {
    sources = new LegendItemSource[] { source };
    items = new BlockContainer(hLayout);
    this.hLayout = hLayout;
    this.vLayout = vLayout;
    backgroundPaint = null;
    legendItemGraphicEdge = RectangleEdge.LEFT;
    legendItemGraphicAnchor = RectangleAnchor.CENTER;
    legendItemGraphicLocation = RectangleAnchor.CENTER;
    legendItemGraphicPadding = new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D);
    itemFont = DEFAULT_ITEM_FONT;
    itemPaint = DEFAULT_ITEM_PAINT;
    itemLabelPadding = new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D);
  }
  




  public LegendItemSource[] getSources()
  {
    return sources;
  }
  





  public void setSources(LegendItemSource[] sources)
  {
    if (sources == null) {
      throw new IllegalArgumentException("Null 'sources' argument.");
    }
    this.sources = sources;
    notifyListeners(new TitleChangeEvent(this));
  }
  




  public Paint getBackgroundPaint()
  {
    return backgroundPaint;
  }
  





  public void setBackgroundPaint(Paint paint)
  {
    backgroundPaint = paint;
    notifyListeners(new TitleChangeEvent(this));
  }
  




  public RectangleEdge getLegendItemGraphicEdge()
  {
    return legendItemGraphicEdge;
  }
  




  public void setLegendItemGraphicEdge(RectangleEdge edge)
  {
    if (edge == null) {
      throw new IllegalArgumentException("Null 'edge' argument.");
    }
    legendItemGraphicEdge = edge;
    notifyListeners(new TitleChangeEvent(this));
  }
  




  public RectangleAnchor getLegendItemGraphicAnchor()
  {
    return legendItemGraphicAnchor;
  }
  




  public void setLegendItemGraphicAnchor(RectangleAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' point.");
    }
    legendItemGraphicAnchor = anchor;
  }
  




  public RectangleAnchor getLegendItemGraphicLocation()
  {
    return legendItemGraphicLocation;
  }
  




  public void setLegendItemGraphicLocation(RectangleAnchor anchor)
  {
    legendItemGraphicLocation = anchor;
  }
  




  public RectangleInsets getLegendItemGraphicPadding()
  {
    return legendItemGraphicPadding;
  }
  





  public void setLegendItemGraphicPadding(RectangleInsets padding)
  {
    if (padding == null) {
      throw new IllegalArgumentException("Null 'padding' argument.");
    }
    legendItemGraphicPadding = padding;
    notifyListeners(new TitleChangeEvent(this));
  }
  




  public Font getItemFont()
  {
    return itemFont;
  }
  





  public void setItemFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    itemFont = font;
    notifyListeners(new TitleChangeEvent(this));
  }
  




  public Paint getItemPaint()
  {
    return itemPaint;
  }
  




  public void setItemPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    itemPaint = paint;
    notifyListeners(new TitleChangeEvent(this));
  }
  




  public RectangleInsets getItemLabelPadding()
  {
    return itemLabelPadding;
  }
  




  public void setItemLabelPadding(RectangleInsets padding)
  {
    if (padding == null) {
      throw new IllegalArgumentException("Null 'padding' argument.");
    }
    itemLabelPadding = padding;
    notifyListeners(new TitleChangeEvent(this));
  }
  


  protected void fetchLegendItems()
  {
    items.clear();
    RectangleEdge p = getPosition();
    if (RectangleEdge.isTopOrBottom(p)) {
      items.setArrangement(hLayout);
    }
    else {
      items.setArrangement(vLayout);
    }
    for (int s = 0; s < sources.length; s++) {
      LegendItemCollection legendItems = sources[s].getLegendItems();
      if (legendItems != null) {
        for (int i = 0; i < legendItems.getItemCount(); i++) {
          LegendItem item = legendItems.get(i);
          Block block = createLegendItemBlock(item);
          items.add(block);
        }
      }
    }
  }
  






  protected Block createLegendItemBlock(LegendItem item)
  {
    BlockContainer result = null;
    LegendGraphic lg = new LegendGraphic(item.getShape(), item.getFillPaint());
    
    lg.setFillPaintTransformer(item.getFillPaintTransformer());
    lg.setShapeFilled(item.isShapeFilled());
    lg.setLine(item.getLine());
    lg.setLineStroke(item.getLineStroke());
    lg.setLinePaint(item.getLinePaint());
    lg.setLineVisible(item.isLineVisible());
    lg.setShapeVisible(item.isShapeVisible());
    lg.setShapeOutlineVisible(item.isShapeOutlineVisible());
    lg.setOutlinePaint(item.getOutlinePaint());
    lg.setOutlineStroke(item.getOutlineStroke());
    lg.setPadding(legendItemGraphicPadding);
    
    LegendItemBlockContainer legendItem = new LegendItemBlockContainer(new BorderArrangement(), item.getDataset(), item.getSeriesKey());
    

    lg.setShapeAnchor(getLegendItemGraphicAnchor());
    lg.setShapeLocation(getLegendItemGraphicLocation());
    legendItem.add(lg, legendItemGraphicEdge);
    Font textFont = item.getLabelFont();
    if (textFont == null) {
      textFont = itemFont;
    }
    Paint textPaint = item.getLabelPaint();
    if (textPaint == null) {
      textPaint = itemPaint;
    }
    LabelBlock labelBlock = new LabelBlock(item.getLabel(), textFont, textPaint);
    
    labelBlock.setPadding(itemLabelPadding);
    legendItem.add(labelBlock);
    legendItem.setToolTipText(item.getToolTipText());
    legendItem.setURLText(item.getURLText());
    
    result = new BlockContainer(new CenterArrangement());
    result.add(legendItem);
    
    return result;
  }
  




  public BlockContainer getItemContainer()
  {
    return items;
  }
  








  public Size2D arrange(Graphics2D g2, RectangleConstraint constraint)
  {
    Size2D result = new Size2D();
    fetchLegendItems();
    if (items.isEmpty()) {
      return result;
    }
    BlockContainer container = wrapper;
    if (container == null) {
      container = items;
    }
    RectangleConstraint c = toContentConstraint(constraint);
    Size2D size = container.arrange(g2, c);
    height = calculateTotalHeight(height);
    width = calculateTotalWidth(width);
    return result;
  }
  






  public void draw(Graphics2D g2, Rectangle2D area)
  {
    draw(g2, area, null);
  }
  









  public Object draw(Graphics2D g2, Rectangle2D area, Object params)
  {
    Rectangle2D target = (Rectangle2D)area.clone();
    Rectangle2D hotspot = (Rectangle2D)area.clone();
    StandardEntityCollection sec = null;
    if (((params instanceof EntityBlockParams)) && (((EntityBlockParams)params).getGenerateEntities()))
    {
      sec = new StandardEntityCollection();
      sec.add(new TitleEntity(hotspot, this));
    }
    target = trimMargin(target);
    if (backgroundPaint != null) {
      g2.setPaint(backgroundPaint);
      g2.fill(target);
    }
    BlockFrame border = getFrame();
    border.draw(g2, target);
    border.getInsets().trim(target);
    BlockContainer container = wrapper;
    if (container == null) {
      container = items;
    }
    target = trimPadding(target);
    Object val = container.draw(g2, target, params);
    if ((val instanceof BlockResult)) {
      EntityCollection ec = ((BlockResult)val).getEntityCollection();
      if ((ec != null) && (sec != null)) {
        sec.addAll(ec);
        ((BlockResult)val).setEntityCollection(sec);
      }
    }
    return val;
  }
  






  public BlockContainer getWrapper()
  {
    return wrapper;
  }
  




  public void setWrapper(BlockContainer wrapper)
  {
    this.wrapper = wrapper;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LegendTitle)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    LegendTitle that = (LegendTitle)obj;
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
      return false;
    }
    if (legendItemGraphicEdge != legendItemGraphicEdge) {
      return false;
    }
    if (legendItemGraphicAnchor != legendItemGraphicAnchor) {
      return false;
    }
    if (legendItemGraphicLocation != legendItemGraphicLocation) {
      return false;
    }
    if (!itemFont.equals(itemFont)) {
      return false;
    }
    if (!itemPaint.equals(itemPaint)) {
      return false;
    }
    if (!hLayout.equals(hLayout)) {
      return false;
    }
    if (!vLayout.equals(vLayout)) {
      return false;
    }
    return true;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(backgroundPaint, stream);
    SerialUtilities.writePaint(itemPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    backgroundPaint = SerialUtilities.readPaint(stream);
    itemPaint = SerialUtilities.readPaint(stream);
  }
}
