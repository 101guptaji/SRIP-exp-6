package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.jfree.date.SerialDate;












































































public class DateChooserPanel
  extends JPanel
  implements ActionListener
{
  private Calendar chosenDate;
  private Color chosenDateButtonColor;
  private Color chosenMonthButtonColor;
  private Color chosenOtherButtonColor;
  private int firstDayOfWeek;
  private int yearSelectionRange = 20;
  



  private Font dateFont = new Font("SansSerif", 0, 10);
  




  private JComboBox monthSelector;
  



  private JComboBox yearSelector;
  



  private JButton todayButton;
  



  private JButton[] buttons;
  



  private boolean refreshing = false;
  



  private int[] WEEK_DAYS;
  




  public DateChooserPanel()
  {
    this(Calendar.getInstance(), false);
  }
  








  public DateChooserPanel(Calendar calendar, boolean controlPanel)
  {
    super(new BorderLayout());
    
    chosenDateButtonColor = UIManager.getColor("textHighlight");
    chosenMonthButtonColor = UIManager.getColor("control");
    chosenOtherButtonColor = UIManager.getColor("controlShadow");
    

    chosenDate = calendar;
    firstDayOfWeek = calendar.getFirstDayOfWeek();
    WEEK_DAYS = new int[7];
    for (int i = 0; i < 7; i++) {
      WEEK_DAYS[i] = ((firstDayOfWeek + i - 1) % 7 + 1);
    }
    
    add(constructSelectionPanel(), "North");
    add(getCalendarPanel(), "Center");
    if (controlPanel) {
      add(constructControlPanel(), "South");
    }
    setDate(calendar.getTime());
  }
  





  public void setDate(Date theDate)
  {
    chosenDate.setTime(theDate);
    monthSelector.setSelectedIndex(chosenDate.get(2));
    
    refreshYearSelector();
    refreshButtons();
  }
  





  public Date getDate()
  {
    return chosenDate.getTime();
  }
  





  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("monthSelectionChanged")) {
      JComboBox c = (JComboBox)e.getSource();
      




      int dayOfMonth = chosenDate.get(5);
      chosenDate.set(5, 1);
      chosenDate.set(2, c.getSelectedIndex());
      int maxDayOfMonth = chosenDate.getActualMaximum(5);
      
      chosenDate.set(5, Math.min(dayOfMonth, maxDayOfMonth));
      
      refreshButtons();
    }
    else if (e.getActionCommand().equals("yearSelectionChanged")) {
      if (!refreshing) {
        JComboBox c = (JComboBox)e.getSource();
        Integer y = (Integer)c.getSelectedItem();
        




        int dayOfMonth = chosenDate.get(5);
        chosenDate.set(5, 1);
        chosenDate.set(1, y.intValue());
        int maxDayOfMonth = chosenDate.getActualMaximum(5);
        
        chosenDate.set(5, Math.min(dayOfMonth, maxDayOfMonth));
        
        refreshYearSelector();
        refreshButtons();
      }
    }
    else if (e.getActionCommand().equals("todayButtonClicked")) {
      setDate(new Date());
    }
    else if (e.getActionCommand().equals("dateButtonClicked")) {
      JButton b = (JButton)e.getSource();
      int i = Integer.parseInt(b.getName());
      Calendar cal = getFirstVisibleDate();
      cal.add(5, i);
      setDate(cal.getTime());
    }
  }
  






  private JPanel getCalendarPanel()
  {
    JPanel p = new JPanel(new GridLayout(7, 7));
    DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
    String[] weekDays = dateFormatSymbols.getShortWeekdays();
    
    for (int i = 0; i < WEEK_DAYS.length; i++) {
      p.add(new JLabel(weekDays[WEEK_DAYS[i]], 0));
    }
    

    buttons = new JButton[42];
    for (int i = 0; i < 42; i++) {
      JButton b = new JButton("");
      b.setMargin(new Insets(1, 1, 1, 1));
      b.setName(Integer.toString(i));
      b.setFont(dateFont);
      b.setFocusPainted(false);
      b.setActionCommand("dateButtonClicked");
      b.addActionListener(this);
      buttons[i] = b;
      p.add(b);
    }
    return p;
  }
  






  private Color getButtonColor(Calendar theDate)
  {
    if (equalDates(theDate, chosenDate)) {
      return chosenDateButtonColor;
    }
    if (theDate.get(2) == chosenDate.get(2))
    {
      return chosenMonthButtonColor;
    }
    
    return chosenOtherButtonColor;
  }
  







  private boolean equalDates(Calendar c1, Calendar c2)
  {
    if ((c1.get(5) == c2.get(5)) && (c1.get(2) == c2.get(2)) && (c1.get(1) == c2.get(1)))
    {

      return true;
    }
    
    return false;
  }
  






  private Calendar getFirstVisibleDate()
  {
    Calendar c = Calendar.getInstance();
    c.set(chosenDate.get(1), chosenDate.get(2), 1);
    
    c.add(5, -1);
    while (c.get(7) != getFirstDayOfWeek()) {
      c.add(5, -1);
    }
    return c;
  }
  





  private int getFirstDayOfWeek()
  {
    return firstDayOfWeek;
  }
  


  private void refreshButtons()
  {
    Calendar c = getFirstVisibleDate();
    for (int i = 0; i < 42; i++) {
      JButton b = buttons[i];
      b.setText(Integer.toString(c.get(5)));
      b.setBackground(getButtonColor(c));
      c.add(5, 1);
    }
  }
  



  private void refreshYearSelector()
  {
    if (!refreshing) {
      refreshing = true;
      yearSelector.removeAllItems();
      Integer[] years = getYears(chosenDate.get(1));
      
      for (int i = 0; i < years.length; i++) {
        yearSelector.addItem(years[i]);
      }
      yearSelector.setSelectedItem(new Integer(chosenDate.get(1)));
      
      refreshing = false;
    }
  }
  







  private Integer[] getYears(int chosenYear)
  {
    int size = yearSelectionRange * 2 + 1;
    int start = chosenYear - yearSelectionRange;
    
    Integer[] years = new Integer[size];
    for (int i = 0; i < size; i++) {
      years[i] = new Integer(i + start);
    }
    return years;
  }
  





  private JPanel constructSelectionPanel()
  {
    JPanel p = new JPanel();
    
    int minMonth = chosenDate.getMinimum(2);
    int maxMonth = chosenDate.getMaximum(2);
    String[] months = new String[maxMonth - minMonth + 1];
    System.arraycopy(SerialDate.getMonths(), minMonth, months, 0, months.length);
    

    monthSelector = new JComboBox(months);
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
  





  public Color getChosenDateButtonColor()
  {
    return chosenDateButtonColor;
  }
  




  public void setChosenDateButtonColor(Color chosenDateButtonColor)
  {
    if (chosenDateButtonColor == null) {
      throw new NullPointerException("UIColor must not be null.");
    }
    Color oldValue = this.chosenDateButtonColor;
    this.chosenDateButtonColor = chosenDateButtonColor;
    refreshButtons();
    firePropertyChange("chosenDateButtonColor", oldValue, chosenDateButtonColor);
  }
  





  public Color getChosenMonthButtonColor()
  {
    return chosenMonthButtonColor;
  }
  




  public void setChosenMonthButtonColor(Color chosenMonthButtonColor)
  {
    if (chosenMonthButtonColor == null) {
      throw new NullPointerException("UIColor must not be null.");
    }
    Color oldValue = this.chosenMonthButtonColor;
    this.chosenMonthButtonColor = chosenMonthButtonColor;
    refreshButtons();
    firePropertyChange("chosenMonthButtonColor", oldValue, chosenMonthButtonColor);
  }
  





  public Color getChosenOtherButtonColor()
  {
    return chosenOtherButtonColor;
  }
  




  public void setChosenOtherButtonColor(Color chosenOtherButtonColor)
  {
    if (chosenOtherButtonColor == null) {
      throw new NullPointerException("UIColor must not be null.");
    }
    Color oldValue = this.chosenOtherButtonColor;
    this.chosenOtherButtonColor = chosenOtherButtonColor;
    refreshButtons();
    firePropertyChange("chosenOtherButtonColor", oldValue, chosenOtherButtonColor);
  }
  





  public int getYearSelectionRange()
  {
    return yearSelectionRange;
  }
  




  public void setYearSelectionRange(int yearSelectionRange)
  {
    int oldYearSelectionRange = this.yearSelectionRange;
    this.yearSelectionRange = yearSelectionRange;
    refreshYearSelector();
    firePropertyChange("yearSelectionRange", oldYearSelectionRange, yearSelectionRange);
  }
}
