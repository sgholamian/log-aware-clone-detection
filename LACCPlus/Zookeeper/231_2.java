//,temp,DuplicateLocalSessionUpgradeTest.java,48,55,temp,LocalSessionRequestTest.java,48,55
//,2
public class xxx {
    @Before
    public void setUp() throws Exception {
        LOG.info("STARTING quorum " + getClass().getName());
        qb.localSessionsEnabled = true;
        qb.localSessionsUpgradingEnabled = true;
        qb.setUp();
        ClientBase.waitForServerUp(qb.hostPort, 10000);
    }

};