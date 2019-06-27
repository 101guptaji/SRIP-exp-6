package Jama.test;

import Jama.CholeskyDecomposition;
import Jama.EigenvalueDecomposition;
import Jama.LUDecomposition;
import Jama.Matrix;
import Jama.QRDecomposition;
import Jama.SingularValueDecomposition;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;







public class TestMatrix
{
  public TestMatrix() {}
  
  public static void main(String[] paramArrayOfString)
  {
    int i = 0;
    int j = 0;
    
    double[] arrayOfDouble1 = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D };
    double[] arrayOfDouble2 = { 1.0D, 4.0D, 7.0D, 10.0D, 2.0D, 5.0D, 8.0D, 11.0D, 3.0D, 6.0D, 9.0D, 12.0D };
    double[][] arrayOfDouble3 = { { 1.0D, 4.0D, 7.0D, 10.0D }, { 2.0D, 5.0D, 8.0D, 11.0D }, { 3.0D, 6.0D, 9.0D, 12.0D } };
    double[][] arrayOfDouble4 = arrayOfDouble3;
    double[][] arrayOfDouble5 = { { 1.0D, 2.0D, 3.0D }, { 4.0D, 5.0D, 6.0D }, { 7.0D, 8.0D, 9.0D }, { 10.0D, 11.0D, 12.0D } };
    double[][] arrayOfDouble6 = { { 5.0D, 8.0D, 11.0D }, { 6.0D, 9.0D, 12.0D } };
    double[][] arrayOfDouble7 = { { 1.0D, 4.0D, 7.0D }, { 2.0D, 5.0D, 8.0D, 11.0D }, { 3.0D, 6.0D, 9.0D, 12.0D } };
    double[][] arrayOfDouble8 = { { 4.0D, 1.0D, 1.0D }, { 1.0D, 2.0D, 3.0D }, { 1.0D, 3.0D, 6.0D } };
    double[][] arrayOfDouble9 = { { 1.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 1.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 1.0D, 0.0D } };
    double[][] arrayOfDouble10 = { { 0.0D, 1.0D, 0.0D, 0.0D }, { 1.0D, 0.0D, 2.0E-7D, 0.0D }, { 0.0D, -2.0E-7D, 0.0D, 1.0D }, { 0.0D, 0.0D, 1.0D, 0.0D } };
    
    double[][] arrayOfDouble11 = { { 166.0D, 188.0D, 210.0D }, { 188.0D, 214.0D, 240.0D }, { 210.0D, 240.0D, 270.0D } };
    double[][] arrayOfDouble12 = { { 13.0D }, { 15.0D } };
    double[][] arrayOfDouble13 = { { 1.0D, 3.0D }, { 7.0D, 9.0D } };
    int k = 3;int m = 4;
    int n = 5;
    int i1 = 0;
    int i2 = 4;
    int i3 = 3;
    int i4 = 4;
    int i5 = 1;int i6 = 2;int i7 = 1;int i8 = 3;
    int[] arrayOfInt1 = { 1, 2 };
    int[] arrayOfInt2 = { 1, 3 };
    int[] arrayOfInt3 = { 1, 2, 3 };
    int[] arrayOfInt4 = { 1, 2, 4 };
    double d2 = 33.0D;
    double d3 = 30.0D;
    double d4 = 15.0D;
    double d5 = 650.0D;
    












    print("\nTesting constructors and constructor-like methods...\n");
    try
    {
      localObject1 = new Matrix(arrayOfDouble1, n);
      i = try_failure(i, "Catch invalid length in packed constructor... ", "exception not thrown for invalid input");
    }
    catch (IllegalArgumentException localIllegalArgumentException1) {
      try_success("Catch invalid length in packed constructor... ", localIllegalArgumentException1.getMessage());
    }
    

    try
    {
      localObject1 = new Matrix(arrayOfDouble7);
      d1 = ((Matrix)localObject1).get(i1, i2);
    } catch (IllegalArgumentException localIllegalArgumentException2) {
      try_success("Catch ragged input to default constructor... ", localIllegalArgumentException2.getMessage());
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1) {
      i = try_failure(i, "Catch ragged input to constructor... ", "exception not thrown in construction...ArrayIndexOutOfBoundsException thrown later");
    }
    

    try
    {
      localObject1 = Matrix.constructWithCopy(arrayOfDouble7);
      d1 = ((Matrix)localObject1).get(i1, i2);
    } catch (IllegalArgumentException localIllegalArgumentException3) {
      try_success("Catch ragged input to constructWithCopy... ", localIllegalArgumentException3.getMessage());
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2) {
      i = try_failure(i, "Catch ragged input to constructWithCopy... ", "exception not thrown in construction...ArrayIndexOutOfBoundsException thrown later");
    }
    
    Object localObject1 = new Matrix(arrayOfDouble1, i3);
    Matrix localMatrix1 = new Matrix(arrayOfDouble3);
    double d1 = localMatrix1.get(0, 0);
    arrayOfDouble3[0][0] = 0.0D;
    Matrix localMatrix2 = localMatrix1.minus((Matrix)localObject1);
    arrayOfDouble3[0][0] = d1;
    localMatrix1 = Matrix.constructWithCopy(arrayOfDouble3);
    d1 = localMatrix1.get(0, 0);
    arrayOfDouble3[0][0] = 0.0D;
    if (d1 - localMatrix1.get(0, 0) != 0.0D)
    {
      i = try_failure(i, "constructWithCopy... ", "copy not effected... data visible outside");
    } else {
      try_success("constructWithCopy... ", "");
    }
    arrayOfDouble3[0][0] = arrayOfDouble1[0];
    Matrix localMatrix5 = new Matrix(arrayOfDouble9);
    try {
      check(localMatrix5, Matrix.identity(3, 4));
      try_success("identity... ", "");
    } catch (RuntimeException localRuntimeException1) {
      i = try_failure(i, "identity... ", "identity Matrix not successfully created");
    }
    




















    print("\nTesting access methods...\n");
    




    localMatrix1 = new Matrix(arrayOfDouble3);
    if (localMatrix1.getRowDimension() != k) {
      i = try_failure(i, "getRowDimension... ", "");
    } else {
      try_success("getRowDimension... ", "");
    }
    if (localMatrix1.getColumnDimension() != m) {
      i = try_failure(i, "getColumnDimension... ", "");
    } else {
      try_success("getColumnDimension... ", "");
    }
    localMatrix1 = new Matrix(arrayOfDouble3);
    double[][] arrayOfDouble = localMatrix1.getArray();
    if (arrayOfDouble != arrayOfDouble3) {
      i = try_failure(i, "getArray... ", "");
    } else {
      try_success("getArray... ", "");
    }
    arrayOfDouble = localMatrix1.getArrayCopy();
    if (arrayOfDouble == arrayOfDouble3) {
      i = try_failure(i, "getArrayCopy... ", "data not (deep) copied");
    }
    try {
      check(arrayOfDouble, arrayOfDouble3);
      try_success("getArrayCopy... ", "");
    } catch (RuntimeException localRuntimeException2) {
      i = try_failure(i, "getArrayCopy... ", "data not successfully (deep) copied");
    }
    double[] arrayOfDouble14 = localMatrix1.getColumnPackedCopy();
    try {
      check(arrayOfDouble14, arrayOfDouble1);
      try_success("getColumnPackedCopy... ", "");
    } catch (RuntimeException localRuntimeException3) {
      i = try_failure(i, "getColumnPackedCopy... ", "data not successfully (deep) copied by columns");
    }
    arrayOfDouble14 = localMatrix1.getRowPackedCopy();
    try {
      check(arrayOfDouble14, arrayOfDouble2);
      try_success("getRowPackedCopy... ", "");
    } catch (RuntimeException localRuntimeException4) {
      i = try_failure(i, "getRowPackedCopy... ", "data not successfully (deep) copied by rows");
    }
    try {
      d1 = localMatrix1.get(localMatrix1.getRowDimension(), localMatrix1.getColumnDimension() - 1);
      i = try_failure(i, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException3) {
      try {
        d1 = localMatrix1.get(localMatrix1.getRowDimension() - 1, localMatrix1.getColumnDimension());
        i = try_failure(i, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException23) {
        try_success("get(int,int)... OutofBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException4) {
      i = try_failure(i, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
    }
    try {
      if (localMatrix1.get(localMatrix1.getRowDimension() - 1, localMatrix1.getColumnDimension() - 1) != arrayOfDouble3[(localMatrix1.getRowDimension() - 1)][(localMatrix1.getColumnDimension() - 1)])
      {
        i = try_failure(i, "get(int,int)... ", "Matrix entry (i,j) not successfully retreived");
      } else {
        try_success("get(int,int)... ", "");
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException4) {
      i = try_failure(i, "get(int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    Matrix localMatrix9 = new Matrix(arrayOfDouble6);
    try {
      localMatrix10 = localMatrix1.getMatrix(i5, i6 + localMatrix1.getRowDimension() + 1, i7, i8);
      i = try_failure(i, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException5) {
      try {
        localMatrix10 = localMatrix1.getMatrix(i5, i6, i7, i8 + localMatrix1.getColumnDimension() + 1);
        i = try_failure(i, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException24) {
        try_success("getMatrix(int,int,int,int)... ArrayIndexOutOfBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException5) {
      i = try_failure(i, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix10 = localMatrix1.getMatrix(i5, i6, i7, i8);
      try {
        check(localMatrix9, localMatrix10);
        try_success("getMatrix(int,int,int,int)... ", "");
      } catch (RuntimeException localRuntimeException5) {
        i = try_failure(i, "getMatrix(int,int,int,int)... ", "submatrix not successfully retreived");
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException6) {
      i = try_failure(i, "getMatrix(int,int,int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    try
    {
      localMatrix10 = localMatrix1.getMatrix(i5, i6, arrayOfInt4);
      i = try_failure(i, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException7) {
      try {
        localMatrix10 = localMatrix1.getMatrix(i5, i6 + localMatrix1.getRowDimension() + 1, arrayOfInt3);
        i = try_failure(i, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException25) {
        try_success("getMatrix(int,int,int[])... ArrayIndexOutOfBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException6) {
      i = try_failure(i, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix10 = localMatrix1.getMatrix(i5, i6, arrayOfInt3);
      try {
        check(localMatrix9, localMatrix10);
        try_success("getMatrix(int,int,int[])... ", "");
      } catch (RuntimeException localRuntimeException6) {
        i = try_failure(i, "getMatrix(int,int,int[])... ", "submatrix not successfully retreived");
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException8) {
      i = try_failure(i, "getMatrix(int,int,int[])... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    try {
      localMatrix10 = localMatrix1.getMatrix(arrayOfInt2, i7, i8);
      i = try_failure(i, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException9) {
      try {
        localMatrix10 = localMatrix1.getMatrix(arrayOfInt1, i7, i8 + localMatrix1.getColumnDimension() + 1);
        i = try_failure(i, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException26) {
        try_success("getMatrix(int[],int,int)... ArrayIndexOutOfBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException7) {
      i = try_failure(i, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix10 = localMatrix1.getMatrix(arrayOfInt1, i7, i8);
      try {
        check(localMatrix9, localMatrix10);
        try_success("getMatrix(int[],int,int)... ", "");
      } catch (RuntimeException localRuntimeException7) {
        i = try_failure(i, "getMatrix(int[],int,int)... ", "submatrix not successfully retreived");
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException10) {
      i = try_failure(i, "getMatrix(int[],int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    try {
      localMatrix10 = localMatrix1.getMatrix(arrayOfInt2, arrayOfInt3);
      i = try_failure(i, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException11) {
      try {
        localMatrix10 = localMatrix1.getMatrix(arrayOfInt1, arrayOfInt4);
        i = try_failure(i, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException27) {
        try_success("getMatrix(int[],int[])... ArrayIndexOutOfBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException8) {
      i = try_failure(i, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix10 = localMatrix1.getMatrix(arrayOfInt1, arrayOfInt3);
      try {
        check(localMatrix9, localMatrix10);
        try_success("getMatrix(int[],int[])... ", "");
      } catch (RuntimeException localRuntimeException8) {
        i = try_failure(i, "getMatrix(int[],int[])... ", "submatrix not successfully retreived");
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException12) {
      i = try_failure(i, "getMatrix(int[],int[])... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    



    try
    {
      localMatrix1.set(localMatrix1.getRowDimension(), localMatrix1.getColumnDimension() - 1, 0.0D);
      i = try_failure(i, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException13) {
      try {
        localMatrix1.set(localMatrix1.getRowDimension() - 1, localMatrix1.getColumnDimension(), 0.0D);
        i = try_failure(i, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException28) {
        try_success("set(int,int,double)... OutofBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException9) {
      i = try_failure(i, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix1.set(i5, i7, 0.0D);
      d1 = localMatrix1.get(i5, i7);
      try {
        check(d1, 0.0D);
        try_success("set(int,int,double)... ", "");
      } catch (RuntimeException localRuntimeException9) {
        i = try_failure(i, "set(int,int,double)... ", "Matrix element not successfully set");
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException14) {
      i = try_failure(i, "set(int,int,double)... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    Matrix localMatrix10 = new Matrix(2, 3, 0.0D);
    try {
      localMatrix1.setMatrix(i5, i6 + localMatrix1.getRowDimension() + 1, i7, i8, localMatrix10);
      i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException15) {
      try {
        localMatrix1.setMatrix(i5, i6, i7, i8 + localMatrix1.getColumnDimension() + 1, localMatrix10);
        i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException29) {
        try_success("setMatrix(int,int,int,int,Matrix)... ArrayIndexOutOfBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException10) {
      i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix1.setMatrix(i5, i6, i7, i8, localMatrix10);
      try {
        check(localMatrix10.minus(localMatrix1.getMatrix(i5, i6, i7, i8)), localMatrix10);
        try_success("setMatrix(int,int,int,int,Matrix)... ", "");
      } catch (RuntimeException localRuntimeException10) {
        i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "submatrix not successfully set");
      }
      localMatrix1.setMatrix(i5, i6, i7, i8, localMatrix9);
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException16) {
      i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    try {
      localMatrix1.setMatrix(i5, i6 + localMatrix1.getRowDimension() + 1, arrayOfInt3, localMatrix10);
      i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException17) {
      try {
        localMatrix1.setMatrix(i5, i6, arrayOfInt4, localMatrix10);
        i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException30) {
        try_success("setMatrix(int,int,int[],Matrix)... ArrayIndexOutOfBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException11) {
      i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix1.setMatrix(i5, i6, arrayOfInt3, localMatrix10);
      try {
        check(localMatrix10.minus(localMatrix1.getMatrix(i5, i6, arrayOfInt3)), localMatrix10);
        try_success("setMatrix(int,int,int[],Matrix)... ", "");
      } catch (RuntimeException localRuntimeException11) {
        i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "submatrix not successfully set");
      }
      localMatrix1.setMatrix(i5, i6, i7, i8, localMatrix9);
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException18) {
      i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    try {
      localMatrix1.setMatrix(arrayOfInt1, i7, i8 + localMatrix1.getColumnDimension() + 1, localMatrix10);
      i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException19) {
      try {
        localMatrix1.setMatrix(arrayOfInt2, i7, i8, localMatrix10);
        i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException31) {
        try_success("setMatrix(int[],int,int,Matrix)... ArrayIndexOutOfBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException12) {
      i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix1.setMatrix(arrayOfInt1, i7, i8, localMatrix10);
      try {
        check(localMatrix10.minus(localMatrix1.getMatrix(arrayOfInt1, i7, i8)), localMatrix10);
        try_success("setMatrix(int[],int,int,Matrix)... ", "");
      } catch (RuntimeException localRuntimeException12) {
        i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "submatrix not successfully set");
      }
      localMatrix1.setMatrix(i5, i6, i7, i8, localMatrix9);
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException20) {
      i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    try {
      localMatrix1.setMatrix(arrayOfInt1, arrayOfInt4, localMatrix10);
      i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException21) {
      try {
        localMatrix1.setMatrix(arrayOfInt2, arrayOfInt3, localMatrix10);
        i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
      } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException32) {
        try_success("setMatrix(int[],int[],Matrix)... ArrayIndexOutOfBoundsException... ", "");
      }
    } catch (IllegalArgumentException localIllegalArgumentException13) {
      i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
    }
    try {
      localMatrix1.setMatrix(arrayOfInt1, arrayOfInt3, localMatrix10);
      try {
        check(localMatrix10.minus(localMatrix1.getMatrix(arrayOfInt1, arrayOfInt3)), localMatrix10);
        try_success("setMatrix(int[],int[],Matrix)... ", "");
      } catch (RuntimeException localRuntimeException13) {
        i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "submatrix not successfully set");
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException22) {
      i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
    }
    















    print("\nTesting array-like methods...\n");
    Matrix localMatrix7 = new Matrix(arrayOfDouble1, i4);
    Matrix localMatrix6 = Matrix.random(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
    localObject1 = localMatrix6;
    try {
      localMatrix7 = ((Matrix)localObject1).minus(localMatrix7);
      i = try_failure(i, "minus conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException14) {
      try_success("minus conformance check... ", "");
    }
    if (((Matrix)localObject1).minus(localMatrix6).norm1() != 0.0D) {
      i = try_failure(i, "minus... ", "(difference of identical Matrices is nonzero,\nSubsequent use of minus should be suspect)");
    } else {
      try_success("minus... ", "");
    }
    localObject1 = localMatrix6.copy();
    ((Matrix)localObject1).minusEquals(localMatrix6);
    Matrix localMatrix3 = new Matrix(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
    try {
      ((Matrix)localObject1).minusEquals(localMatrix7);
      i = try_failure(i, "minusEquals conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException15) {
      try_success("minusEquals conformance check... ", "");
    }
    if (((Matrix)localObject1).minus(localMatrix3).norm1() != 0.0D) {
      i = try_failure(i, "minusEquals... ", "(difference of identical Matrices is nonzero,\nSubsequent use of minus should be suspect)");
    } else {
      try_success("minusEquals... ", "");
    }
    
    localObject1 = localMatrix6.copy();
    localMatrix1 = Matrix.random(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
    localMatrix2 = ((Matrix)localObject1).minus(localMatrix1);
    try {
      localMatrix7 = ((Matrix)localObject1).plus(localMatrix7);
      i = try_failure(i, "plus conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException16) {
      try_success("plus conformance check... ", "");
    }
    try {
      check(localMatrix2.plus(localMatrix1), (Matrix)localObject1);
      try_success("plus... ", "");
    } catch (RuntimeException localRuntimeException14) {
      i = try_failure(i, "plus... ", "(C = A - B, but C + B != A)");
    }
    localMatrix2 = ((Matrix)localObject1).minus(localMatrix1);
    localMatrix2.plusEquals(localMatrix1);
    try {
      ((Matrix)localObject1).plusEquals(localMatrix7);
      i = try_failure(i, "plusEquals conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException17) {
      try_success("plusEquals conformance check... ", "");
    }
    try {
      check(localMatrix2, (Matrix)localObject1);
      try_success("plusEquals... ", "");
    } catch (RuntimeException localRuntimeException15) {
      i = try_failure(i, "plusEquals... ", "(C = A - B, but C = C + B != A)");
    }
    localObject1 = localMatrix6.uminus();
    try {
      check(((Matrix)localObject1).plus(localMatrix6), localMatrix3);
      try_success("uminus... ", "");
    } catch (RuntimeException localRuntimeException16) {
      i = try_failure(i, "uminus... ", "(-A + A != zeros)");
    }
    localObject1 = localMatrix6.copy();
    Matrix localMatrix4 = new Matrix(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension(), 1.0D);
    localMatrix2 = ((Matrix)localObject1).arrayLeftDivide(localMatrix6);
    try {
      localMatrix7 = ((Matrix)localObject1).arrayLeftDivide(localMatrix7);
      i = try_failure(i, "arrayLeftDivide conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException18) {
      try_success("arrayLeftDivide conformance check... ", "");
    }
    try {
      check(localMatrix2, localMatrix4);
      try_success("arrayLeftDivide... ", "");
    } catch (RuntimeException localRuntimeException17) {
      i = try_failure(i, "arrayLeftDivide... ", "(M.\\M != ones)");
    }
    try {
      ((Matrix)localObject1).arrayLeftDivideEquals(localMatrix7);
      i = try_failure(i, "arrayLeftDivideEquals conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException19) {
      try_success("arrayLeftDivideEquals conformance check... ", "");
    }
    ((Matrix)localObject1).arrayLeftDivideEquals(localMatrix6);
    try {
      check((Matrix)localObject1, localMatrix4);
      try_success("arrayLeftDivideEquals... ", "");
    } catch (RuntimeException localRuntimeException18) {
      i = try_failure(i, "arrayLeftDivideEquals... ", "(M.\\M != ones)");
    }
    localObject1 = localMatrix6.copy();
    try {
      ((Matrix)localObject1).arrayRightDivide(localMatrix7);
      i = try_failure(i, "arrayRightDivide conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException20) {
      try_success("arrayRightDivide conformance check... ", "");
    }
    localMatrix2 = ((Matrix)localObject1).arrayRightDivide(localMatrix6);
    try {
      check(localMatrix2, localMatrix4);
      try_success("arrayRightDivide... ", "");
    } catch (RuntimeException localRuntimeException19) {
      i = try_failure(i, "arrayRightDivide... ", "(M./M != ones)");
    }
    try {
      ((Matrix)localObject1).arrayRightDivideEquals(localMatrix7);
      i = try_failure(i, "arrayRightDivideEquals conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException21) {
      try_success("arrayRightDivideEquals conformance check... ", "");
    }
    ((Matrix)localObject1).arrayRightDivideEquals(localMatrix6);
    try {
      check((Matrix)localObject1, localMatrix4);
      try_success("arrayRightDivideEquals... ", "");
    } catch (RuntimeException localRuntimeException20) {
      i = try_failure(i, "arrayRightDivideEquals... ", "(M./M != ones)");
    }
    localObject1 = localMatrix6.copy();
    localMatrix1 = Matrix.random(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
    try {
      localMatrix7 = ((Matrix)localObject1).arrayTimes(localMatrix7);
      i = try_failure(i, "arrayTimes conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException22) {
      try_success("arrayTimes conformance check... ", "");
    }
    localMatrix2 = ((Matrix)localObject1).arrayTimes(localMatrix1);
    try {
      check(localMatrix2.arrayRightDivideEquals(localMatrix1), (Matrix)localObject1);
      try_success("arrayTimes... ", "");
    } catch (RuntimeException localRuntimeException21) {
      i = try_failure(i, "arrayTimes... ", "(A = R, C = A.*B, but C./B != A)");
    }
    try {
      ((Matrix)localObject1).arrayTimesEquals(localMatrix7);
      i = try_failure(i, "arrayTimesEquals conformance check... ", "nonconformance not raised");
    } catch (IllegalArgumentException localIllegalArgumentException23) {
      try_success("arrayTimesEquals conformance check... ", "");
    }
    ((Matrix)localObject1).arrayTimesEquals(localMatrix1);
    try {
      check(((Matrix)localObject1).arrayRightDivideEquals(localMatrix1), localMatrix6);
      try_success("arrayTimesEquals... ", "");
    } catch (RuntimeException localRuntimeException22) {
      i = try_failure(i, "arrayTimesEquals... ", "(A = R, A = A.*B, but A./B != R)");
    }
    








    print("\nTesting I/O methods...\n");
    Object localObject3;
    try { DecimalFormat localDecimalFormat = new DecimalFormat("0.0000E00");
      localDecimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
      
      localObject2 = new PrintWriter(new FileOutputStream("JamaTestMatrix.out"));
      ((Matrix)localObject1).print((PrintWriter)localObject2, localDecimalFormat, 10);
      ((PrintWriter)localObject2).close();
      localMatrix6 = Matrix.read(new BufferedReader(new FileReader("JamaTestMatrix.out")));
      if (((Matrix)localObject1).minus(localMatrix6).norm1() < 0.001D) {
        try_success("print()/read()...", "");
      } else {
        i = try_failure(i, "print()/read()...", "Matrix read from file does not match Matrix printed to file");
      }
    } catch (IOException localIOException1) {
      j = try_warning(j, "print()/read()...", "unexpected I/O error, unable to run print/read test;  check write permission in current directory and retry");
    } catch (Exception localException1) {
      try {
        localException1.printStackTrace(System.out);
        j = try_warning(j, "print()/read()...", "Formatting error... will try JDK1.1 reformulation...");
        Object localObject2 = new DecimalFormat("0.0000");
        localObject3 = new PrintWriter(new FileOutputStream("JamaTestMatrix.out"));
        ((Matrix)localObject1).print((PrintWriter)localObject3, (NumberFormat)localObject2, 10);
        ((PrintWriter)localObject3).close();
        localMatrix6 = Matrix.read(new BufferedReader(new FileReader("JamaTestMatrix.out")));
        if (((Matrix)localObject1).minus(localMatrix6).norm1() < 0.001D) {
          try_success("print()/read()...", "");
        } else {
          i = try_failure(i, "print()/read() (2nd attempt) ...", "Matrix read from file does not match Matrix printed to file");
        }
      } catch (IOException localIOException2) {
        j = try_warning(j, "print()/read()...", "unexpected I/O error, unable to run print/read test;  check write permission in current directory and retry");
      }
    }
    
    localMatrix6 = Matrix.random(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
    String str = "TMPMATRIX.serial";
    try {
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(new FileOutputStream(str));
      localObjectOutputStream.writeObject(localMatrix6);
      localObject3 = new ObjectInputStream(new FileInputStream(str));
      localObject1 = (Matrix)((ObjectInputStream)localObject3).readObject();
      try
      {
        check((Matrix)localObject1, localMatrix6);
        try_success("writeObject(Matrix)/readObject(Matrix)...", "");
      } catch (RuntimeException localRuntimeException32) {
        i = try_failure(i, "writeObject(Matrix)/readObject(Matrix)...", "Matrix not serialized correctly");
      }
    } catch (IOException localIOException3) {
      j = try_warning(j, "writeObject()/readObject()...", "unexpected I/O error, unable to run serialization test;  check write permission in current directory and retry");
    } catch (Exception localException2) {
      i = try_failure(i, "writeObject(Matrix)/readObject(Matrix)...", "unexpected error in serialization test");
    }
    






















    print("\nTesting linear algebra methods...\n");
    localObject1 = new Matrix(arrayOfDouble1, 3);
    Matrix localMatrix11 = new Matrix(arrayOfDouble5);
    localMatrix11 = ((Matrix)localObject1).transpose();
    try {
      check(((Matrix)localObject1).transpose(), localMatrix11);
      try_success("transpose...", "");
    } catch (RuntimeException localRuntimeException23) {
      i = try_failure(i, "transpose()...", "transpose unsuccessful");
    }
    ((Matrix)localObject1).transpose();
    try {
      check(((Matrix)localObject1).norm1(), d2);
      try_success("norm1...", "");
    } catch (RuntimeException localRuntimeException24) {
      i = try_failure(i, "norm1()...", "incorrect norm calculation");
    }
    try {
      check(((Matrix)localObject1).normInf(), d3);
      try_success("normInf()...", "");
    } catch (RuntimeException localRuntimeException25) {
      i = try_failure(i, "normInf()...", "incorrect norm calculation");
    }
    try {
      check(((Matrix)localObject1).normF(), Math.sqrt(d5));
      try_success("normF...", "");
    } catch (RuntimeException localRuntimeException26) {
      i = try_failure(i, "normF()...", "incorrect norm calculation");
    }
    try {
      check(((Matrix)localObject1).trace(), d4);
      try_success("trace()...", "");
    } catch (RuntimeException localRuntimeException27) {
      i = try_failure(i, "trace()...", "incorrect trace calculation");
    }
    try {
      check(((Matrix)localObject1).getMatrix(0, ((Matrix)localObject1).getRowDimension() - 1, 0, ((Matrix)localObject1).getRowDimension() - 1).det(), 0.0D);
      try_success("det()...", "");
    } catch (RuntimeException localRuntimeException28) {
      i = try_failure(i, "det()...", "incorrect determinant calculation");
    }
    Matrix localMatrix12 = new Matrix(arrayOfDouble11);
    try {
      check(((Matrix)localObject1).times(((Matrix)localObject1).transpose()), localMatrix12);
      try_success("times(Matrix)...", "");
    } catch (RuntimeException localRuntimeException29) {
      i = try_failure(i, "times(Matrix)...", "incorrect Matrix-Matrix product calculation");
    }
    try {
      check(((Matrix)localObject1).times(0.0D), localMatrix3);
      try_success("times(double)...", "");
    } catch (RuntimeException localRuntimeException30) {
      i = try_failure(i, "times(double)...", "incorrect Matrix-scalar product calculation");
    }
    
    localObject1 = new Matrix(arrayOfDouble1, 4);
    QRDecomposition localQRDecomposition = ((Matrix)localObject1).qr();
    localMatrix6 = localQRDecomposition.getR();
    try {
      check((Matrix)localObject1, localQRDecomposition.getQ().times(localMatrix6));
      try_success("QRDecomposition...", "");
    } catch (RuntimeException localRuntimeException31) {
      i = try_failure(i, "QRDecomposition...", "incorrect QR decomposition calculation");
    }
    SingularValueDecomposition localSingularValueDecomposition = ((Matrix)localObject1).svd();
    try {
      check((Matrix)localObject1, localSingularValueDecomposition.getU().times(localSingularValueDecomposition.getS().times(localSingularValueDecomposition.getV().transpose())));
      try_success("SingularValueDecomposition...", "");
    } catch (RuntimeException localRuntimeException33) {
      i = try_failure(i, "SingularValueDecomposition...", "incorrect singular value decomposition calculation");
    }
    Matrix localMatrix13 = new Matrix(arrayOfDouble4);
    try {
      check(localMatrix13.rank(), Math.min(localMatrix13.getRowDimension(), localMatrix13.getColumnDimension()) - 1);
      try_success("rank()...", "");
    } catch (RuntimeException localRuntimeException34) {
      i = try_failure(i, "rank()...", "incorrect rank calculation");
    }
    localMatrix1 = new Matrix(arrayOfDouble13);
    localSingularValueDecomposition = localMatrix1.svd();
    double[] arrayOfDouble15 = localSingularValueDecomposition.getSingularValues();
    try {
      check(localMatrix1.cond(), arrayOfDouble15[0] / arrayOfDouble15[(Math.min(localMatrix1.getRowDimension(), localMatrix1.getColumnDimension()) - 1)]);
      try_success("cond()...", "");
    } catch (RuntimeException localRuntimeException35) {
      i = try_failure(i, "cond()...", "incorrect condition number calculation");
    }
    int i9 = ((Matrix)localObject1).getColumnDimension();
    localObject1 = ((Matrix)localObject1).getMatrix(0, i9 - 1, 0, i9 - 1);
    ((Matrix)localObject1).set(0, 0, 0.0D);
    LUDecomposition localLUDecomposition = ((Matrix)localObject1).lu();
    try {
      check(((Matrix)localObject1).getMatrix(localLUDecomposition.getPivot(), 0, i9 - 1), localLUDecomposition.getL().times(localLUDecomposition.getU()));
      try_success("LUDecomposition...", "");
    } catch (RuntimeException localRuntimeException36) {
      i = try_failure(i, "LUDecomposition...", "incorrect LU decomposition calculation");
    }
    Matrix localMatrix8 = ((Matrix)localObject1).inverse();
    try {
      check(((Matrix)localObject1).times(localMatrix8), Matrix.identity(3, 3));
      try_success("inverse()...", "");
    } catch (RuntimeException localRuntimeException37) {
      i = try_failure(i, "inverse()...", "incorrect inverse calculation");
    }
    localMatrix4 = new Matrix(localMatrix9.getRowDimension(), 1, 1.0D);
    Matrix localMatrix14 = new Matrix(arrayOfDouble12);
    localMatrix12 = localMatrix9.getMatrix(0, localMatrix9.getRowDimension() - 1, 0, localMatrix9.getRowDimension() - 1);
    try {
      check(localMatrix12.solve(localMatrix14), localMatrix4);
      try_success("solve()...", "");
    } catch (IllegalArgumentException localIllegalArgumentException24) {
      i = try_failure(i, "solve()...", localIllegalArgumentException24.getMessage());
    } catch (RuntimeException localRuntimeException38) {
      i = try_failure(i, "solve()...", localRuntimeException38.getMessage());
    }
    localObject1 = new Matrix(arrayOfDouble8);
    CholeskyDecomposition localCholeskyDecomposition = ((Matrix)localObject1).chol();
    Matrix localMatrix15 = localCholeskyDecomposition.getL();
    try {
      check((Matrix)localObject1, localMatrix15.times(localMatrix15.transpose()));
      try_success("CholeskyDecomposition...", "");
    } catch (RuntimeException localRuntimeException39) {
      i = try_failure(i, "CholeskyDecomposition...", "incorrect Cholesky decomposition calculation");
    }
    localMatrix8 = localCholeskyDecomposition.solve(Matrix.identity(3, 3));
    try {
      check(((Matrix)localObject1).times(localMatrix8), Matrix.identity(3, 3));
      try_success("CholeskyDecomposition solve()...", "");
    } catch (RuntimeException localRuntimeException40) {
      i = try_failure(i, "CholeskyDecomposition solve()...", "incorrect Choleskydecomposition solve calculation");
    }
    EigenvalueDecomposition localEigenvalueDecomposition = ((Matrix)localObject1).eig();
    Matrix localMatrix16 = localEigenvalueDecomposition.getD();
    Matrix localMatrix17 = localEigenvalueDecomposition.getV();
    try {
      check(((Matrix)localObject1).times(localMatrix17), localMatrix17.times(localMatrix16));
      try_success("EigenvalueDecomposition (symmetric)...", "");
    } catch (RuntimeException localRuntimeException41) {
      i = try_failure(i, "EigenvalueDecomposition (symmetric)...", "incorrect symmetric Eigenvalue decomposition calculation");
    }
    localObject1 = new Matrix(arrayOfDouble10);
    localEigenvalueDecomposition = ((Matrix)localObject1).eig();
    localMatrix16 = localEigenvalueDecomposition.getD();
    localMatrix17 = localEigenvalueDecomposition.getV();
    try {
      check(((Matrix)localObject1).times(localMatrix17), localMatrix17.times(localMatrix16));
      try_success("EigenvalueDecomposition (nonsymmetric)...", "");
    } catch (RuntimeException localRuntimeException42) {
      i = try_failure(i, "EigenvalueDecomposition (nonsymmetric)...", "incorrect nonsymmetric Eigenvalue decomposition calculation");
    }
    
    print("\nTestMatrix completed.\n");
    print("Total errors reported: " + Integer.toString(i) + "\n");
    print("Total warnings reported: " + Integer.toString(j) + "\n");
  }
  



  private static void check(double paramDouble1, double paramDouble2)
  {
    double d = Math.pow(2.0D, -52.0D);
    if (((paramDouble1 == 0.0D ? 1 : 0) & (Math.abs(paramDouble2) < 10.0D * d ? 1 : 0)) != 0) return;
    if (((paramDouble2 == 0.0D ? 1 : 0) & (Math.abs(paramDouble1) < 10.0D * d ? 1 : 0)) != 0) return;
    if (Math.abs(paramDouble1 - paramDouble2) > 10.0D * d * Math.max(Math.abs(paramDouble1), Math.abs(paramDouble2))) {
      throw new RuntimeException("The difference x-y is too large: x = " + Double.toString(paramDouble1) + "  y = " + Double.toString(paramDouble2));
    }
  }
  

  private static void check(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    if (paramArrayOfDouble1.length == paramArrayOfDouble2.length) {
      for (int i = 0; i < paramArrayOfDouble1.length; i++) {
        check(paramArrayOfDouble1[i], paramArrayOfDouble2[i]);
      }
    } else {
      throw new RuntimeException("Attempt to compare vectors of different lengths");
    }
  }
  

  private static void check(double[][] paramArrayOfDouble1, double[][] paramArrayOfDouble2)
  {
    Matrix localMatrix1 = new Matrix(paramArrayOfDouble1);
    Matrix localMatrix2 = new Matrix(paramArrayOfDouble2);
    check(localMatrix1, localMatrix2);
  }
  

  private static void check(Matrix paramMatrix1, Matrix paramMatrix2)
  {
    double d = Math.pow(2.0D, -52.0D);
    if (((paramMatrix1.norm1() == 0.0D ? 1 : 0) & (paramMatrix2.norm1() < 10.0D * d ? 1 : 0)) != 0) return;
    if (((paramMatrix2.norm1() == 0.0D ? 1 : 0) & (paramMatrix1.norm1() < 10.0D * d ? 1 : 0)) != 0) return;
    if (paramMatrix1.minus(paramMatrix2).norm1() > 1000.0D * d * Math.max(paramMatrix1.norm1(), paramMatrix2.norm1())) {
      throw new RuntimeException("The norm of (X-Y) is too large: " + Double.toString(paramMatrix1.minus(paramMatrix2).norm1()));
    }
  }
  

  private static void print(String paramString)
  {
    System.out.print(paramString);
  }
  

  private static void try_success(String paramString1, String paramString2)
  {
    print(">    " + paramString1 + "success\n");
    if (paramString2 != "") {
      print(">      Message: " + paramString2 + "\n");
    }
  }
  
  private static int try_failure(int paramInt, String paramString1, String paramString2)
  {
    print(">    " + paramString1 + "*** failure ***\n>      Message: " + paramString2 + "\n");
    paramInt++;return paramInt;
  }
  

  private static int try_warning(int paramInt, String paramString1, String paramString2)
  {
    print(">    " + paramString1 + "*** warning ***\n>      Message: " + paramString2 + "\n");
    paramInt++;return paramInt;
  }
  


  private static void print(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
  {
    System.out.print("\n");
    new Matrix(paramArrayOfDouble, 1).print(paramInt1, paramInt2);
    print("\n");
  }
}
