//,temp,NativeIO.java,716,726,temp,ShortCircuitShm.java,59,68
//,3
public class xxx {
  static long getOperatingSystemPageSize() {
    try {
      Field f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      Unsafe unsafe = (Unsafe)f.get(null);
      return unsafe.pageSize();
    } catch (Throwable e) {
      LOG.warn("Unable to get operating system page size.  Guessing 4096.", e);
      return 4096;
    }
  }

};