//,temp,GenericTestUtils.java,152,165,temp,TestHRegion.java,6089,6099
//,3
public class xxx {
      @Override
      public Void call() throws Exception {
        LOG.info("Acquiring row lock");
        RowLock rl = region.getRowLock(b);
        obtainedRowLock.countDown();
        LOG.info("Waiting for 5 seconds before releasing lock");
        Threads.sleep(5000);
        LOG.info("Releasing row lock");
        rl.release();
        return null;
      }

};