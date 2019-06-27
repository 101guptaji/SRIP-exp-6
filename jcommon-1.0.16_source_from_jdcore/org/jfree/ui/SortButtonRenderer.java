package org.jfree.ui;

import java.awt.Component;
import java.awt.Insets;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;




































































public class SortButtonRenderer
  implements TableCellRenderer
{
  public static final int NONE = 0;
  public static final int DOWN = 1;
  public static final int UP = 2;
  private int pressedColumn = -1;
  




  private JButton normalButton;
  



  private JButton ascendingButton;
  



  private JButton descendingButton;
  



  private boolean useLabels;
  



  private JLabel normalLabel;
  



  private JLabel ascendingLabel;
  



  private JLabel descendingLabel;
  




  public SortButtonRenderer()
  {
    pressedColumn = -1;
    useLabels = UIManager.getLookAndFeel().getID().equals("Aqua");
    
    Border border = UIManager.getBorder("TableHeader.cellBorder");
    
    if (useLabels) {
      normalLabel = new JLabel();
      normalLabel.setHorizontalAlignment(10);
      
      ascendingLabel = new JLabel();
      ascendingLabel.setHorizontalAlignment(10);
      ascendingLabel.setHorizontalTextPosition(2);
      ascendingLabel.setIcon(new BevelArrowIcon(1, false, false));
      
      descendingLabel = new JLabel();
      descendingLabel.setHorizontalAlignment(10);
      descendingLabel.setHorizontalTextPosition(2);
      descendingLabel.setIcon(new BevelArrowIcon(0, false, false));
      
      normalLabel.setBorder(border);
      ascendingLabel.setBorder(border);
      descendingLabel.setBorder(border);
    }
    else {
      normalButton = new JButton();
      normalButton.setMargin(new Insets(0, 0, 0, 0));
      normalButton.setHorizontalAlignment(10);
      
      ascendingButton = new JButton();
      ascendingButton.setMargin(new Insets(0, 0, 0, 0));
      ascendingButton.setHorizontalAlignment(10);
      ascendingButton.setHorizontalTextPosition(2);
      ascendingButton.setIcon(new BevelArrowIcon(1, false, false));
      ascendingButton.setPressedIcon(new BevelArrowIcon(1, false, true));
      
      descendingButton = new JButton();
      descendingButton.setMargin(new Insets(0, 0, 0, 0));
      descendingButton.setHorizontalAlignment(10);
      descendingButton.setHorizontalTextPosition(2);
      descendingButton.setIcon(new BevelArrowIcon(0, false, false));
      descendingButton.setPressedIcon(new BevelArrowIcon(0, false, true));
      
      normalButton.setBorder(border);
      ascendingButton.setBorder(border);
      descendingButton.setBorder(border);
    }
  }
  

















  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    if (table == null) {
      throw new NullPointerException("Table must not be null.");
    }
    

    SortableTableModel model = (SortableTableModel)table.getModel();
    int cc = table.convertColumnIndexToModel(column);
    boolean isSorting = model.getSortingColumn() == cc;
    boolean isAscending = model.isAscending();
    
    JTableHeader header = table.getTableHeader();
    boolean isPressed = cc == pressedColumn;
    JComponent component;
    JComponent component; if (useLabels) {
      JLabel label = getRendererLabel(isSorting, isAscending);
      label.setText(value == null ? "" : value.toString());
      component = label;
    }
    else {
      JButton button = getRendererButton(isSorting, isAscending);
      button.setText(value == null ? "" : value.toString());
      button.getModel().setPressed(isPressed);
      button.getModel().setArmed(isPressed);
      component = button;
    }
    
    if (header != null) {
      component.setForeground(header.getForeground());
      component.setBackground(header.getBackground());
      component.setFont(header.getFont());
    }
    return component;
  }
  






  private JButton getRendererButton(boolean isSorting, boolean isAscending)
  {
    if (isSorting) {
      if (isAscending) {
        return ascendingButton;
      }
      
      return descendingButton;
    }
    

    return normalButton;
  }
  







  private JLabel getRendererLabel(boolean isSorting, boolean isAscending)
  {
    if (isSorting) {
      if (isAscending) {
        return ascendingLabel;
      }
      
      return descendingLabel;
    }
    

    return normalLabel;
  }
  





  public void setPressedColumn(int column)
  {
    pressedColumn = column;
  }
}
