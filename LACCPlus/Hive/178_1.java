//,temp,TestReplicationScenariosAcidTables.java,732,744,temp,TestReplicationScenariosAcidTables.java,659,670
//,3
public class xxx {
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

};