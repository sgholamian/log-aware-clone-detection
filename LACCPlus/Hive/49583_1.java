//,temp,TestReplicationScenariosAcidTables.java,723,755,temp,TestReplicationScenariosAcidTables.java,650,681
//,3
public class xxx {
      @Nullable
      @Override
      public Boolean apply(@Nullable CallerArguments args) {
        if (injectionPathCalled) {
          nonInjectedPathCalled = true;
        } else {
          // Insert another row to t1 and drop the table from another txn when bootstrap dump in progress.
          injectionPathCalled = true;
          Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
              LOG.info("Entered new thread");
              IDriver driver = DriverFactory.newDriver(primaryConf);
              SessionState.start(new CliSessionState(primaryConf));
              try {
                driver.run("insert into " + primaryDbName + ".t1 values(2)");
                driver.run("drop table " + primaryDbName + ".t1");
              } catch (CommandProcessorException e) {
                throw new RuntimeException(e);
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
        return true;
      }

};