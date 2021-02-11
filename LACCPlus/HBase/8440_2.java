//,temp,UnsafeAvailChecker.java,39,50,temp,UnsafeAccess.java,54,64
//,3
public class xxx {
      @Override
      public Object run() {
        try {
          Field f = Unsafe.class.getDeclaredField("theUnsafe");
          f.setAccessible(true);
          return f.get(null);
        } catch (Throwable e) {
          LOG.warn("sun.misc.Unsafe is not accessible", e);
        }
        return null;
      }

};