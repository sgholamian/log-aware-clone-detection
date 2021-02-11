//,temp,UnsafeAvailChecker.java,39,50,temp,UnsafeAccess.java,54,64
//,3
public class xxx {
      @Override
      public Boolean run() {
        try {
          Class<?> clazz = Class.forName(CLASS_NAME);
          Field f = clazz.getDeclaredField("theUnsafe");
          f.setAccessible(true);
          return f.get(null) != null;
        } catch (Throwable e) {
          LOG.warn("sun.misc.Unsafe is not available/accessible", e);
        }
        return false;
      }

};