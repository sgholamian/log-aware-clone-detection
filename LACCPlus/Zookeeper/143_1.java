//,temp,DuplicateLocalSessionUpgradeTest.java,48,55,temp,LocalSessionsOnlyTest.java,51,58
//,3
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