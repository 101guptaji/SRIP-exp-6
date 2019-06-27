package junit.framework;




public class ComparisonFailure
  extends AssertionFailedError
{
  private String fExpected;
  


  private String fActual;
  


  public ComparisonFailure(String message, String expected, String actual)
  {
    super(message);
    fExpected = expected;
    fActual = actual;
  }
  





  public String getMessage()
  {
    if ((fExpected == null) || (fActual == null)) {
      return Assert.format(super.getMessage(), fExpected, fActual);
    }
    int end = Math.min(fExpected.length(), fActual.length());
    
    for (int i = 0; 
        i < end; i++) {
      if (fExpected.charAt(i) != fActual.charAt(i))
        break;
    }
    int j = fExpected.length() - 1;
    int k = fActual.length() - 1;
    for (; (k >= i) && (j >= i); j--) {
      if (fExpected.charAt(j) != fActual.charAt(k)) {
        break;
      }
      k--;
    }
    
    String actual;
    
    String expected;
    String actual;
    if ((j < i) && (k < i)) {
      String expected = fExpected;
      actual = fActual;
    } else {
      expected = fExpected.substring(i, j + 1);
      actual = fActual.substring(i, k + 1);
      if ((i <= end) && (i > 0)) {
        expected = "..." + expected;
        actual = "..." + actual;
      }
      
      if (j < fExpected.length() - 1)
        expected = expected + "...";
      if (k < fActual.length() - 1)
        actual = actual + "...";
    }
    return Assert.format(super.getMessage(), expected, actual);
  }
}
