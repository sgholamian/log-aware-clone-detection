//,temp,PublicNetworkTest.java,87,102,temp,NetworkProviderTest.java,134,149
//,2
public class xxx {
    @AfterClass
    public static void globalTearDown() throws Exception {
        s_lockMaster.cleanupForServer(s_msId);
        JmxUtil.unregisterMBean("Locks", "Locks");
        s_lockMaster = null;

        AbstractApplicationContext ctx = (AbstractApplicationContext)ComponentContext.getApplicationContext();
        Map<String, ComponentLifecycle> lifecycleComponents = ctx.getBeansOfType(ComponentLifecycle.class);
        for (ComponentLifecycle bean : lifecycleComponents.values()) {
            bean.stop();
        }
        ctx.close();

        s_logger.info("destroying mysql server instance running at port <" + s_mysqlSrverPort + ">");
        TestDbSetup.destroy(s_mysqlSrverPort, null);
    }

};