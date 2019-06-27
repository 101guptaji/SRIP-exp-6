package org.jfree.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;






























































public class SortedConfigurationWriter
{
  private static final int ESCAPE_KEY = 0;
  private static final int ESCAPE_VALUE = 1;
  private static final int ESCAPE_COMMENT = 2;
  private static final String END_OF_LINE = ;
  






  public SortedConfigurationWriter() {}
  






  protected String getDescription(String key)
  {
    return null;
  }
  







  public void save(String filename, Configuration config)
    throws IOException
  {
    save(new File(filename), config);
  }
  







  public void save(File file, Configuration config)
    throws IOException
  {
    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
    
    save(out, config);
    out.close();
  }
  







  public void save(OutputStream outStream, Configuration config)
    throws IOException
  {
    ArrayList names = new ArrayList();
    

    Iterator defaults = config.findPropertyKeys("");
    while (defaults.hasNext()) {
      String key = (String)defaults.next();
      names.add(key);
    }
    
    Collections.sort(names);
    
    OutputStreamWriter out = new OutputStreamWriter(outStream, "iso-8859-1");
    

    for (int i = 0; i < names.size(); i++) {
      String key = (String)names.get(i);
      String value = config.getConfigProperty(key);
      
      String description = getDescription(key);
      if (description != null) {
        writeDescription(description, out);
      }
      saveConvert(key, 0, out);
      out.write("=");
      saveConvert(value, 1, out);
      out.write(END_OF_LINE);
    }
    out.flush();
  }
  









  private void writeDescription(String text, Writer writer)
    throws IOException
  {
    if (text.length() == 0) {
      return;
    }
    
    writer.write("# ");
    writer.write(END_OF_LINE);
    LineBreakIterator iterator = new LineBreakIterator(text);
    while (iterator.hasNext()) {
      writer.write("# ");
      saveConvert((String)iterator.next(), 2, writer);
      writer.write(END_OF_LINE);
    }
  }
  









  private void saveConvert(String text, int escapeMode, Writer writer)
    throws IOException
  {
    char[] string = text.toCharArray();
    
    for (int x = 0; x < string.length; x++) {
      char aChar = string[x];
      switch (aChar)
      {
      case ' ': 
        if ((escapeMode != 2) && ((x == 0) || (escapeMode == 0)))
        {
          writer.write(92);
        }
        writer.write(32);
        break;
      

      case '\\': 
        writer.write(92);
        writer.write(92);
        break;
      

      case '\t': 
        if (escapeMode == 2) {
          writer.write(aChar);
        }
        else {
          writer.write(92);
          writer.write(116);
        }
        break;
      

      case '\n': 
        writer.write(92);
        writer.write(110);
        break;
      

      case '\r': 
        writer.write(92);
        writer.write(114);
        break;
      

      case '\f': 
        if (escapeMode == 2) {
          writer.write(aChar);
        }
        else {
          writer.write(92);
          writer.write(102);
        }
        break;
      

      case '!': 
      case '"': 
      case '#': 
      case ':': 
      case '=': 
        if (escapeMode == 2) {
          writer.write(aChar);
        }
        else {
          writer.write(92);
          writer.write(aChar);
        }
        break;
      
      default: 
        if ((aChar < ' ') || (aChar > '~')) {
          writer.write(92);
          writer.write(117);
          writer.write(HEX_CHARS[(aChar >> '\f' & 0xF)]);
          writer.write(HEX_CHARS[(aChar >> '\b' & 0xF)]);
          writer.write(HEX_CHARS[(aChar >> '\004' & 0xF)]);
          writer.write(HEX_CHARS[(aChar & 0xF)]);
        }
        else {
          writer.write(aChar);
        }
        break;
      }
    }
  }
  
  private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}
