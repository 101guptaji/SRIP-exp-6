package org.jfree.threads;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;




































public class ReaderWriterLock
{
  private ArrayList waiters;
  
  private static class ReaderWriterNode
  {
    protected static final int READER = 0;
    protected static final int WRITER = 1;
    protected Thread t;
    protected int state;
    protected int nAcquires;
    
    ReaderWriterNode(Thread x0, int x1, ReaderWriterLock.1 x2)
    {
      this(x0, x1);
    }
    



















    private ReaderWriterNode(Thread t, int state)
    {
      this.t = t;
      this.state = state;
      nAcquires = 0;
    }
  }
  






  public ReaderWriterLock()
  {
    waiters = new ArrayList();
  }
  



  public synchronized void lockRead()
  {
    Thread me = Thread.currentThread();
    int index = getIndex(me);
    ReaderWriterNode node; if (index == -1) {
      ReaderWriterNode node = new ReaderWriterNode(me, 0, null);
      waiters.add(node);
    }
    else {
      node = (ReaderWriterNode)waiters.get(index);
    }
    while (getIndex(me) > firstWriter()) {
      try {
        wait();
      }
      catch (Exception e) {
        System.err.println("ReaderWriterLock.lockRead(): exception.");
        System.err.print(e.getMessage());
      }
    }
    nAcquires += 1;
  }
  



  public synchronized void lockWrite()
  {
    Thread me = Thread.currentThread();
    int index = getIndex(me);
    ReaderWriterNode node; if (index == -1) {
      ReaderWriterNode node = new ReaderWriterNode(me, 1, null);
      waiters.add(node);
    }
    else {
      node = (ReaderWriterNode)waiters.get(index);
      if (state == 0) {
        throw new IllegalArgumentException("Upgrade lock");
      }
      state = 1;
    }
    while (getIndex(me) != 0) {
      try {
        wait();
      }
      catch (Exception e) {
        System.err.println("ReaderWriterLock.lockWrite(): exception.");
        System.err.print(e.getMessage());
      }
    }
    nAcquires += 1;
  }
  




  public synchronized void unlock()
  {
    Thread me = Thread.currentThread();
    int index = getIndex(me);
    if (index > firstWriter()) {
      throw new IllegalArgumentException("Lock not held");
    }
    ReaderWriterNode node = (ReaderWriterNode)waiters.get(index);
    nAcquires -= 1;
    if (nAcquires == 0) {
      waiters.remove(index);
    }
    notifyAll();
  }
  




  private int firstWriter()
  {
    Iterator e = waiters.iterator();
    int index = 0;
    while (e.hasNext()) {
      ReaderWriterNode node = (ReaderWriterNode)e.next();
      if (state == 1) {
        return index;
      }
      index++;
    }
    return Integer.MAX_VALUE;
  }
  






  private int getIndex(Thread t)
  {
    Iterator e = waiters.iterator();
    int index = 0;
    while (e.hasNext()) {
      ReaderWriterNode node = (ReaderWriterNode)e.next();
      if (t == t) {
        return index;
      }
      index++;
    }
    return -1;
  }
}
