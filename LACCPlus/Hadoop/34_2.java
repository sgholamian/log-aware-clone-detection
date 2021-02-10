//,temp,MapTask.java,1989,1999,temp,ClientBaseWithFixes.java,461,473
//,3
public class xxx {
    protected void tearDownAll() throws Exception {
        synchronized (this) {
            if (allClients != null) for (ZooKeeper zk : allClients) {
                try {
                    if (zk != null)
                        zk.close();
                } catch (InterruptedException e) {
                    LOG.warn("ignoring interrupt", e);
                }
            }
            allClients = null;
        }
    }

};