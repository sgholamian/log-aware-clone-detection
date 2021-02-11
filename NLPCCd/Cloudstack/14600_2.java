//,temp,sample_5103.java,2,15,temp,sample_8894.java,2,15
//,2
public class xxx {
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


log.info("destroying mysql server instance running at port");
}

};