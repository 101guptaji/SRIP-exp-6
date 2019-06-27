package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.date.SerialDate;





















































public class SerialDateChooserPanel
  extends JPanel
  implements ActionListener
{
  public static final Color DEFAULT_DATE_BUTTON_COLOR = Color.red;
  

  public static final Color DEFAULT_MONTH_BUTTON_COLOR = Color.lightGray;
  

  private SerialDate date;
  

  private Color dateButtonColor;
  

  private Color monthButtonColor;
  

  private Color chosenOtherButtonColor = Color.darkGray;
  

  private int firstDayOfWeek = 1;
  

  private int yearSelectionRange = 20;
  

  private Font dateFont = new Font("SansSerif", 0, 10);
  

  private JComboBox monthSelector = null;
  

  private JComboBox yearSelector = null;
  

  private JButton todayButton = null;
  

  private JButton[] buttons = null;
  

  private boolean refreshing = false;
  



  public SerialDateChooserPanel()
  {
    this(SerialDate.createInstance(new Date()), false, DEFAULT_DATE_BUTTON_COLOR, DEFAULT_MONTH_BUTTON_COLOR);
  }
  










  public SerialDateChooserPanel(SerialDate date, boolean controlPanel)
  {
    this(date, controlPanel, DEFAULT_DATE_BUTTON_COLOR, DEFAULT_MONTH_BUTTON_COLOR);
  }
  












  public SerialDateChooserPanel(SerialDate date, boolean controlPanel, Color dateButtonColor, Color monthButtonColor)
  {
    super(new BorderLayout());
    
    this.date = date;
    this.dateButtonColor = dateButtonColor;
    this.monthButtonColor = monthButtonColor;
    
    add(constructSelectionPanel(), "North");
    add(getCalendarPanel(), "Center");
    if (controlPanel) {
      add(constructControlPanel(), "South");
    }
  }
  






  public void setDate(SerialDate date)
  {
    this.date = date;
    monthSelector.setSelectedIndex(date.getMonth() - 1);
    refreshYearSelector();
    refreshButtons();
  }
  





  public SerialDate getDate()
  {
    return date;
  }
  





  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("monthSelectionChanged")) {
      JComboBox c = (JComboBox)e.getSource();
      date = SerialDate.createInstance(date.getDayOfMonth(), c.getSelectedIndex() + 1, date.getYYYY());
      

      refreshButtons();
    }
    else if (e.getActionCommand().equals("yearSelectionChanged")) {
      if (!refreshing) {
        JComboBox c = (JComboBox)e.getSource();
        Integer y = (Integer)c.getSelectedItem();
        date = SerialDate.createInstance(date.getDayOfMonth(), date.getMonth(), y.intValue());
        

        refreshYearSelector();
        refreshButtons();
      }
    }
    else if (e.getActionCommand().equals("todayButtonClicked")) {
      setDate(SerialDate.createInstance(new Date()));
    }
    else if (e.getActionCommand().equals("dateButtonClicked")) {
      JButton b = (JButton)e.getSource();
      int i = Integer.parseInt(b.getName());
      SerialDate first = getFirstVisibleDate();
      SerialDate selected = SerialDate.addDays(i, first);
      setDate(selected);
    }
  }
  







  private JPanel getCalendarPanel()
  {
    JPanel panel = new JPanel(new GridLayout(7, 7));
    panel.add(new JLabel("Sun", 0));
    panel.add(new JLabel("Mon", 0));
    panel.add(new JLabel("Tue", 0));
    panel.add(new JLabel("Wed", 0));
    panel.add(new JLabel("Thu", 0));
    panel.add(new JLabel("Fri", 0));
    panel.add(new JLabel("Sat", 0));
    
    buttons = new JButton[42];
    for (int i = 0; i < 42; i++) {
      JButton button = new JButton("");
      button.setMargin(new Insets(1, 1, 1, 1));
      button.setName(Integer.toString(i));
      button.setFont(dateFont);
      button.setFocusPainted(false);
      button.setActionCommand("dateButtonClicked");
      button.addActionListener(this);
      buttons[i] = button;
      panel.add(button);
    }
    return panel;
  }
  








  protected Color getButtonColor(SerialDate targetDate)
  {
    if (date.equals(date)) {
      return dateButtonColor;
    }
    if (targetDate.getMonth() == date.getMonth()) {
      return monthButtonColor;
    }
    
    return chosenOtherButtonColor;
  }
  








  protected SerialDate getFirstVisibleDate()
  {
    SerialDate result = SerialDate.createInstance(1, date.getMonth(), date.getYYYY());
    result = SerialDate.addDays(-1, result);
    while (result.getDayOfWeek() != getFirstDayOfWeek()) {
      result = SerialDate.addDays(-1, result);
    }
    return result;
  }
  





  private int getFirstDayOfWeek()
  {
    return firstDayOfWeek;
  }
  



  protected void refreshButtons()
  {
    SerialDate current = getFirstVisibleDate();
    for (int i = 0; i < 42; i++) {
      JButton button = buttons[i];
      button.setText(String.valueOf(current.getDayOfWeek()));
      button.setBackground(getButtonColor(current));
      current = SerialDate.addDays(1, current);
    }
  }
  




  private void refreshYearSelector()
  {
    if (!refreshing) {
      refreshing = true;
      yearSelector.removeAllItems();
      Vector v = getYears(date.getYYYY());
      for (Enumeration e = v.elements(); e.hasMoreElements();) {
        yearSelector.addItem(e.nextElement());
      }
      yearSelector.setSelectedItem(new Integer(date.getYYYY()));
      refreshing = false;
    }
  }
  







  private Vector getYears(int chosenYear)
  {
    Vector v = new Vector();
    for (int i = chosenYear - yearSelectionRange; 
        i <= chosenYear + yearSelectionRange; i++) {
      v.addElement(new Integer(i));
    }
    return v;
  }
  





  private JPanel constructSelectionPanel()
  {
    JPanel p = new JPanel();
    monthSelector = new JComboBox(SerialDate.getMonths());
    monthSelector.addActionListener(this);
    monthSelector.setActionCommand("monthSelectionChanged");
    p.add(monthSelector);
    
    yearSelector = new JComboBox(getYears(0));
    yearSelector.addActionListener(this);
    yearSelector.setActionCommand("yearSelectionChanged");
    p.add(yearSelector);
    
    return p;
  }
  






  private JPanel constructControlPanel()
  {
    JPanel p = new JPanel();
    p.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    todayButton = new JButton("Today");
    todayButton.addActionListener(this);
    todayButton.setActionCommand("todayButtonClicked");
    p.add(todayButton);
    return p;
  }
}
