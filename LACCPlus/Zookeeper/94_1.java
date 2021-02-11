//,temp,ClientHammerTest.java,92,110,temp,ClientHammerTest.java,60,77
//,3
public class xxx {
        public void run() {
            byte b[] = new byte[256];
            try {
                for (; current < count; current++) {
                    ZooKeeper zk = parent.createClient();
                    try {
                        zk.create(prefix + current, b, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    } finally {
                        try {
                            zk.close();
                        } catch (InterruptedException e) {
                            LOG.warn("Unexpected", e);
                        }
                    }
                }
            } catch (Throwable t) {
                LOG.error("Client create operation Assert.failed", t);
            }
        }

};