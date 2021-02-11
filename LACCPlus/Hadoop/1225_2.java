//,temp,NativeIO.java,682,692,temp,ShortCircuitShm.java,55,64
//,3
public class xxx {
  private static Unsafe safetyDance() {
    try {
      Field f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      return (Unsafe)f.get(null);
    } catch (Throwable e) {
      LOG.error("failed to load misc.Unsafe", e);
    }
    return null;
  }

};