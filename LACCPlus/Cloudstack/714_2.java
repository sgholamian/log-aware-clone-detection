//,temp,PublicNetworkTest.java,77,85,temp,NetworkProviderTest.java,123,132
//,2
public class xxx {
    @BeforeClass
    public static void globalSetUp() throws Exception {
        ApiConnectorFactory.setImplementation(ApiConnectorMock.class);
        s_logger.info("mysql server is getting launched ");
        s_mysqlSrverPort = TestDbSetup.init(null);
        s_logger.info("mysql server launched on port " + s_mysqlSrverPort);

        s_msId = ManagementServerNode.getManagementServerId();
        s_lockMaster = Merovingian2.createLockMaster(s_msId);
    }

};