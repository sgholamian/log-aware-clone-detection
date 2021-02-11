//,temp,PublicNetworkTest.java,104,118,temp,NetworkProviderTest.java,151,168
//,3
public class xxx {
    @Override
    @Before
    public void setUp() throws Exception {
        try {
            ComponentContext.initComponentsLifeCycle();
        } catch (Exception ex) {
            ex.printStackTrace();
            s_logger.error(ex.getMessage());
        }
        Account system = _accountMgr.getSystemAccount();
        User user = _accountMgr.getSystemUser();
        CallContext.register(user, system);
        _server = ComponentContext.inject(new ManagementServerMock());

        _server.initialize(!s_initDone);
        s_initDone = false;
        _api = _contrailMgr.getApiConnector();
    }

};