//,temp,InvalidSnapCountTest.java,77,86,temp,ZooKeeperServerMainTest.java,113,122
//,2
public class xxx {
        public void run() {
            String args[] = new String[1];
            args[0] = confFile.toString();
            try {
                main.initializeAndRun(args);
            } catch (Exception e) {
                // test will still fail even though we just log/ignore
                LOG.error("unexpected exception in run", e);
            }
        }

};