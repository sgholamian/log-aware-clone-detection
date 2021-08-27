//,temp,FailoverManagedClusterTest.java,116,125,temp,AMQ4222Test.java,98,108
//,3
public class xxx {
            @Override
            public void run() {
                try {
                    slaveThreadStarted.countDown();
                    slave.start();
                    LOG.info("slave has started");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

};