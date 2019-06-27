import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.StringTokenizer;

class svm_scale
{
  private String line = null;
  private double lower = -1.0D;
  private double upper = 1.0D;
  private double y_lower;
  private double y_upper;
  private boolean y_scaling = false;
  private double[] feature_max;
  private double[] feature_min;
  private double y_max = -1.7976931348623157E308D;
  private double y_min = Double.MAX_VALUE;
  private int max_index;
  private long num_nonzeros = 0L;
  private long new_num_nonzeros = 0L;
  
  svm_scale() {}
  
  private static void exit_with_help() { System.out.print("Usage: svm-scale [options] data_filename\noptions:\n-l lower : x scaling lower limit (default -1)\n-u upper : x scaling upper limit (default +1)\n-y y_lower y_upper : y scaling limits (default: no y scaling)\n-s save_filename : save scaling parameters to save_filename\n-r restore_filename : restore scaling parameters from restore_filename\n");
    







    System.exit(1);
  }
  
  private BufferedReader rewind(BufferedReader paramBufferedReader, String paramString) throws java.io.IOException
  {
    paramBufferedReader.close();
    return new BufferedReader(new java.io.FileReader(paramString));
  }
  
  private void output_target(double paramDouble)
  {
    if (y_scaling)
    {
      if (paramDouble == y_min) {
        paramDouble = y_lower;
      } else if (paramDouble == y_max) {
        paramDouble = y_upper;
      } else {
        paramDouble = y_lower + (y_upper - y_lower) * (paramDouble - y_min) / (y_max - y_min);
      }
    }
    
    System.out.print(paramDouble + " ");
  }
  

  private void output(int paramInt, double paramDouble)
  {
    if (feature_max[paramInt] == feature_min[paramInt]) {
      return;
    }
    if (paramDouble == feature_min[paramInt]) {
      paramDouble = lower;
    } else if (paramDouble == feature_max[paramInt]) {
      paramDouble = upper;
    } else {
      paramDouble = lower + (upper - lower) * (paramDouble - feature_min[paramInt]) / (feature_max[paramInt] - feature_min[paramInt]);
    }
    

    if (paramDouble != 0.0D)
    {
      System.out.print(paramInt + ":" + paramDouble + " ");
      new_num_nonzeros += 1L;
    }
  }
  
  private String readline(BufferedReader paramBufferedReader) throws java.io.IOException
  {
    line = paramBufferedReader.readLine();
    return line;
  }
  
  private void run(String[] paramArrayOfString)
    throws java.io.IOException
  {
    BufferedReader localBufferedReader1 = null;BufferedReader localBufferedReader2 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    

    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (paramArrayOfString[i].charAt(0) != '-') break;
      i++;
      switch (paramArrayOfString[(i - 1)].charAt(1)) {
      case 'l': 
        lower = Double.parseDouble(paramArrayOfString[i]); break;
      case 'u':  upper = Double.parseDouble(paramArrayOfString[i]); break;
      case 'y': 
        y_lower = Double.parseDouble(paramArrayOfString[i]);
        i++;
        y_upper = Double.parseDouble(paramArrayOfString[i]);
        y_scaling = true;
        break;
      case 's':  str1 = paramArrayOfString[i]; break;
      case 'r':  str2 = paramArrayOfString[i]; break;
      case 'm': case 'n': case 'o': case 'p': case 'q': case 't': case 'v': case 'w': case 'x': default: 
        System.err.println("unknown option");
        exit_with_help();
      }
      
    }
    if ((upper <= lower) || ((y_scaling) && (y_upper <= y_lower)))
    {
      System.err.println("inconsistent lower/upper specification");
      System.exit(1);
    }
    if ((str2 != null) && (str1 != null))
    {
      System.err.println("cannot use -r and -s simultaneously");
      System.exit(1);
    }
    
    if (paramArrayOfString.length != i + 1) {
      exit_with_help();
    }
    str3 = paramArrayOfString[i];
    try {
      localBufferedReader1 = new BufferedReader(new java.io.FileReader(str3));
    } catch (Exception localException1) {
      System.err.println("can't open file " + str3);
      System.exit(1);
    }
    


    max_index = 0;
    
    if (str2 != null)
    {

      try
      {
        localBufferedReader2 = new BufferedReader(new java.io.FileReader(str2));
      }
      catch (Exception localException2) {
        System.err.println("can't open file " + str2);
        System.exit(1); }
      int i1;
      if ((i1 = localBufferedReader2.read()) == 121)
      {
        localBufferedReader2.readLine();
        localBufferedReader2.readLine();
        localBufferedReader2.readLine();
      }
      localBufferedReader2.readLine();
      localBufferedReader2.readLine();
      
      String str4 = null;
      while ((str4 = localBufferedReader2.readLine()) != null)
      {
        StringTokenizer localStringTokenizer2 = new StringTokenizer(str4);
        int k = Integer.parseInt(localStringTokenizer2.nextToken());
        max_index = Math.max(max_index, k);
      }
      localBufferedReader2 = rewind(localBufferedReader2, str2);
    }
    int j;
    while (readline(localBufferedReader1) != null)
    {
      StringTokenizer localStringTokenizer1 = new StringTokenizer(line, " \t\n\r\f:");
      localStringTokenizer1.nextToken();
      while (localStringTokenizer1.hasMoreTokens())
      {
        j = Integer.parseInt(localStringTokenizer1.nextToken());
        max_index = Math.max(max_index, j);
        localStringTokenizer1.nextToken();
        num_nonzeros += 1L;
      }
    }
    try
    {
      feature_max = new double[max_index + 1];
      feature_min = new double[max_index + 1];
    } catch (OutOfMemoryError localOutOfMemoryError) {
      System.err.println("can't allocate enough memory");
      System.exit(1);
    }
    
    for (i = 0; i <= max_index; i++)
    {
      feature_max[i] = -1.7976931348623157E308D;
      feature_min[i] = Double.MAX_VALUE;
    }
    
    localBufferedReader1 = rewind(localBufferedReader1, str3);
    int m;
    StringTokenizer localStringTokenizer3;
    double d4; while (readline(localBufferedReader1) != null)
    {
      m = 1;
      


      localStringTokenizer3 = new StringTokenizer(line, " \t\n\r\f:");
      double d1 = Double.parseDouble(localStringTokenizer3.nextToken());
      y_max = Math.max(y_max, d1);
      y_min = Math.min(y_min, d1);
      
      while (localStringTokenizer3.hasMoreTokens())
      {
        j = Integer.parseInt(localStringTokenizer3.nextToken());
        d4 = Double.parseDouble(localStringTokenizer3.nextToken());
        
        for (i = m; i < j; i++)
        {
          feature_max[i] = Math.max(feature_max[i], 0.0D);
          feature_min[i] = Math.min(feature_min[i], 0.0D);
        }
        
        feature_max[j] = Math.max(feature_max[j], d4);
        feature_min[j] = Math.min(feature_min[j], d4);
        m = j + 1;
      }
      
      for (i = m; i <= max_index; i++)
      {
        feature_max[i] = Math.max(feature_max[i], 0.0D);
        feature_min[i] = Math.min(feature_min[i], 0.0D);
      }
    }
    
    localBufferedReader1 = rewind(localBufferedReader1, str3);
    

    if (str2 != null)
    {




      localBufferedReader2.mark(2);
      int i2; StringTokenizer localStringTokenizer4; if ((i2 = localBufferedReader2.read()) == 121)
      {
        localBufferedReader2.readLine();
        localStringTokenizer4 = new StringTokenizer(localBufferedReader2.readLine());
        y_lower = Double.parseDouble(localStringTokenizer4.nextToken());
        y_upper = Double.parseDouble(localStringTokenizer4.nextToken());
        localStringTokenizer4 = new StringTokenizer(localBufferedReader2.readLine());
        y_min = Double.parseDouble(localStringTokenizer4.nextToken());
        y_max = Double.parseDouble(localStringTokenizer4.nextToken());
        y_scaling = true;
      }
      else {
        localBufferedReader2.reset();
      }
      if (localBufferedReader2.read() == 120) {
        localBufferedReader2.readLine();
        localStringTokenizer4 = new StringTokenizer(localBufferedReader2.readLine());
        lower = Double.parseDouble(localStringTokenizer4.nextToken());
        upper = Double.parseDouble(localStringTokenizer4.nextToken());
        String str5 = null;
        while ((str5 = localBufferedReader2.readLine()) != null)
        {
          StringTokenizer localStringTokenizer5 = new StringTokenizer(str5);
          m = Integer.parseInt(localStringTokenizer5.nextToken());
          double d3 = Double.parseDouble(localStringTokenizer5.nextToken());
          double d5 = Double.parseDouble(localStringTokenizer5.nextToken());
          if (m <= max_index)
          {
            feature_min[m] = d3;
            feature_max[m] = d5;
          }
        }
      }
      localBufferedReader2.close();
    }
    
    if (str1 != null)
    {
      Formatter localFormatter = new Formatter(new StringBuilder());
      java.io.BufferedWriter localBufferedWriter = null;
      try
      {
        localBufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(str1));
      } catch (java.io.IOException localIOException) {
        System.err.println("can't open file " + str1);
        System.exit(1);
      }
      
      if (y_scaling)
      {
        localFormatter.format("y\n", new Object[0]);
        localFormatter.format("%.16g %.16g\n", new Object[] { Double.valueOf(y_lower), Double.valueOf(y_upper) });
        localFormatter.format("%.16g %.16g\n", new Object[] { Double.valueOf(y_min), Double.valueOf(y_max) });
      }
      localFormatter.format("x\n", new Object[0]);
      localFormatter.format("%.16g %.16g\n", new Object[] { Double.valueOf(lower), Double.valueOf(upper) });
      for (i = 1; i <= max_index; i++)
      {
        if (feature_min[i] != feature_max[i])
          localFormatter.format("%d %.16g %.16g\n", new Object[] { Integer.valueOf(i), Double.valueOf(feature_min[i]), Double.valueOf(feature_max[i]) });
      }
      localBufferedWriter.write(localFormatter.toString());
      localBufferedWriter.close();
    }
    

    while (readline(localBufferedReader1) != null)
    {
      int n = 1;
      


      localStringTokenizer3 = new StringTokenizer(line, " \t\n\r\f:");
      double d2 = Double.parseDouble(localStringTokenizer3.nextToken());
      output_target(d2);
      while (localStringTokenizer3.hasMoreElements())
      {
        j = Integer.parseInt(localStringTokenizer3.nextToken());
        d4 = Double.parseDouble(localStringTokenizer3.nextToken());
        for (i = n; i < j; i++)
          output(i, 0.0D);
        output(j, d4);
        n = j + 1;
      }
      
      for (i = n; i <= max_index; i++)
        output(i, 0.0D);
      System.out.print("\n");
    }
    if (new_num_nonzeros > num_nonzeros) {
      System.err.print("WARNING: original #nonzeros " + num_nonzeros + "\n" + "         new      #nonzeros " + new_num_nonzeros + "\n" + "Use -l 0 if many original feature values are zeros\n");
    }
    


    localBufferedReader1.close();
  }
  
  public static void main(String[] paramArrayOfString) throws java.io.IOException
  {
    svm_scale localSvm_scale = new svm_scale();
    localSvm_scale.run(paramArrayOfString);
  }
}
