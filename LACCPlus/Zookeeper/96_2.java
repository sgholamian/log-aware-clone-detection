//,temp,ChrootClientTest.java,31,48,temp,ChrootAsyncTest.java,30,46
//,3
public class xxx {
    @Override
    public void setUp() throws Exception {
        String hp = hostPort;
        hostPort = hostPort + "/chrootasynctest";

        super.setUp();

        LOG.info("Creating client " + getTestName());

        ZooKeeper zk = createClient(hp);
        try {
            zk.create("/chrootasynctest", null, Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        } finally {
            zk.close();
        }
    }

};