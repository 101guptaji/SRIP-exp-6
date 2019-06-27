package libsvm;





class Cache
{
  private final int l;
  



  private long size;
  



  private final head_t[] head;
  


  private head_t lru_head;
  



  Cache(int paramInt, long paramLong)
  {
    l = paramInt;
    size = paramLong;
    head = new head_t[l];
    for (int i = 0; i < l; i++) head[i] = new head_t(null);
    size /= 4L;
    size -= l * 4;
    size = Math.max(size, 2L * l);
    lru_head = new head_t(null);
    lru_head.next = (lru_head.prev = lru_head);
  }
  

  private void lru_delete(head_t paramHead_t)
  {
    prev.next = next;
    next.prev = prev;
  }
  

  private void lru_insert(head_t paramHead_t)
  {
    next = lru_head;
    prev = lru_head.prev;
    prev.next = paramHead_t;
    next.prev = paramHead_t;
  }
  




  int get_data(int paramInt1, float[][] paramArrayOfFloat, int paramInt2)
  {
    head_t localHead_t = head[paramInt1];
    if (len > 0) lru_delete(localHead_t);
    int i = paramInt2 - len;
    
    if (i > 0)
    {

      while (size < i)
      {
        localObject = lru_head.next;
        lru_delete((head_t)localObject);
        size += len;
        data = null;
        len = 0;
      }
      

      Object localObject = new float[paramInt2];
      if (data != null) System.arraycopy(data, 0, localObject, 0, len);
      data = ((float[])localObject);
      size -= i;
      int j = len;len = paramInt2;paramInt2 = j;
    }
    
    lru_insert(localHead_t);
    paramArrayOfFloat[0] = data;
    return paramInt2;
  }
  
  void swap_index(int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2) { return;
    }
    if (head[paramInt1].len > 0) lru_delete(head[paramInt1]);
    if (head[paramInt2].len > 0) lru_delete(head[paramInt2]);
    float[] arrayOfFloat = head[paramInt1].data;head[paramInt1].data = head[paramInt2].data;head[paramInt2].data = arrayOfFloat;
    int i = head[paramInt1].len;head[paramInt1].len = head[paramInt2].len;head[paramInt2].len = i;
    if (head[paramInt1].len > 0) lru_insert(head[paramInt1]);
    if (head[paramInt2].len > 0) { lru_insert(head[paramInt2]);
    }
    if (paramInt1 > paramInt2) { i = paramInt1;paramInt1 = paramInt2;paramInt2 = i; }
    for (head_t localHead_t = lru_head.next; localHead_t != lru_head; localHead_t = next)
    {
      if (len > paramInt1)
      {
        if (len > paramInt2) {
          float f = data[paramInt1];data[paramInt1] = data[paramInt2];data[paramInt2] = f;
        }
        else
        {
          lru_delete(localHead_t);
          size += len;
          data = null;
          len = 0;
        }
      }
    }
  }
  
  private final class head_t
  {
    head_t prev;
    head_t next;
    float[] data;
    int len;
    
    private head_t() {}
  }
}
