//,temp,MetricsSystemImpl.java,316,326,temp,ServiceOperations.java,100,108
//,3
public class xxx {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args)
              throws Throwable {
            try {
              return method.invoke(callback, args);
            } catch (Exception e) {
              // These are not considered fatal.
              LOG.warn("Caught exception in callback " + method.getName(), e);
            }
            return null;
          }

};