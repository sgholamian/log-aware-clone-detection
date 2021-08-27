//,temp,TestReplicationScenariosAcidTablesBootstrap.java,257,287,temp,TestReplicationScenarios.java,879,911
//,3
public class xxx {
      @Nullable
      @Override
      public CurrentNotificationEventId apply(@Nullable CurrentNotificationEventId input) {
        if (injectionPathCalled) {
          nonInjectedPathCalled = true;
        } else {
          // Do some writes through concurrent thread
          injectionPathCalled = true;
          Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
              LOG.info("Entered new thread");
              try {
                prepareInc2NonAcidData(primaryDbName, primary.hiveConf);
                prepareInc2AcidData(primaryDbName, primary.hiveConf);
              } catch (Throwable t) {
                Assert.assertNull(t);
              }
              LOG.info("Exit new thread success");
            }
          });
          t.start();
          LOG.info("Created new thread {}", t.getName());
          try {
            t.join();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
        return input;
      }

};