//,temp,FairScheduler.java,644,653,temp,ServiceOperations.java,100,108
//,3
public class xxx {
  public static Exception stopQuietly(Logger log, Service service) {
    try {
      stop(service);
    } catch (Exception e) {
      log.warn("When stopping the service {}", service.getName(), e);
      return e;
    }
    return null;
  }

};