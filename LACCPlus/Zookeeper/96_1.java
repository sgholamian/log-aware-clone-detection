//,temp,ChrootClientTest.java,31,48,temp,ChrootAsyncTest.java,30,46
//,3
public class xxx {
    @Override
    public void setUp() throws Exception {
        String hp = hostPort;
        hostPort = hostPort + "/chrootclienttest";

        System.out.println(hostPort);
        super.setUp();

        LOG.info("STARTING " + getTestName());

        ZooKeeper zk = createClient(hp);
        try {
            zk.create("/chrootclienttest", null, Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        } finally {
            zk.close();
        }
    }

};