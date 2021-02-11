//,temp,ClientBaseWithFixes.java,436,448,temp,FederationRMFailoverProxyProvider.java,203,215
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