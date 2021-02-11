//,temp,UTF8.java,325,339,temp,UTF8.java,87,109
//,3
public class xxx {
  public void set(String string) {
    if (string.length() > 0xffff/3) {             // maybe too long
      LOG.warn("truncating long string: " + string.length()
               + " chars, starting with " + string.substring(0, 20));
      string = string.substring(0, 0xffff/3);
    }

    length = utf8Length(string);                  // compute length
    if (length > 0xffff)                          // double-check length
      throw new RuntimeException("string too long!");

    if (bytes == null || length > bytes.length)   // grow buffer
      bytes = new byte[length];

    try {                                         // avoid sync'd allocations
      DataOutputBuffer obuf = OBUF_FACTORY.get();
      obuf.reset();
      writeChars(obuf, string, 0, string.length());
      System.arraycopy(obuf.getData(), 0, bytes, 0, length);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

};