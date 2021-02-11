//,temp,TestChoreService.java,132,140,temp,TestChoreService.java,91,98
//,3
public class xxx {
      @Override
      protected boolean initialChore() {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          log.warn("", e);
        }
        return true;
      }

};