//,temp,TestReplicationScenariosAcidTablesBootstrap.java,257,287,temp,TestReplicationScenarios.java,879,911
//,3
public class xxx {
      @Nullable
      @Override
      public Table apply(@Nullable Table table) {
        if (injectionPathCalled) {
          nonInjectedPathCalled = true;
        } else {
          // getTable is invoked after fetching the table names
          injectionPathCalled = true;
          Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
              LOG.info("Entered new thread");
              IDriver driver2 = DriverFactory.newDriver(hconf);
              SessionState.start(new CliSessionState(hconf));
              try {
                driver2.run("ALTER TABLE " + dbName + ".ptned PARTITION (b=1) RENAME TO PARTITION (b=10)");
                driver2.run("ALTER TABLE " + dbName + ".ptned RENAME TO " + dbName + ".ptned_renamed");
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
        return table;
      }

};