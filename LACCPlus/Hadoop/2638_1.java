//,temp,TestFileCreationEmpty.java,44,50,temp,DistCpSync.java,261,270
//,3
public class xxx {
      @Override
      public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof ConcurrentModificationException) {
          LeaseManager.LOG.error("t=" + t, e);
          isConcurrentModificationException = true;
        }
      }

};