//,temp,TestChoreService.java,132,140,temp,TestChoreService.java,91,98
//,3
public class xxx {
      @Override
      protected void chore() {
        try {
          Thread.sleep(getPeriod() * 2);
        } catch (InterruptedException e) {
          log.warn("", e);
        }
      }

};