//,temp,UTF8.java,325,339,temp,UTF8.java,87,109
//,3
public class xxx {
  public static int writeString(DataOutput out, String s) throws IOException {
    if (s.length() > 0xffff/3) {         // maybe too long
      LOG.warn("truncating long string: " + s.length()
               + " chars, starting with " + s.substring(0, 20));
      s = s.substring(0, 0xffff/3);
    }

    int len = utf8Length(s);
    if (len > 0xffff)                             // double-check length
      throw new IOException("string too long!");
      
    out.writeShort(len);
    writeChars(out, s, 0, s.length());
    return len;
  }

};