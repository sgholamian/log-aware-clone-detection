//,temp,sample_5225.java,2,12,temp,sample_5224.java,2,16
//,3
public class xxx {
public void testHMConnectorServerWhenStopMaster() throws Exception {
conf.set(CoprocessorHost.MASTER_COPROCESSOR_CONF_KEY, JMXListener.class.getName() + "," + MyAccessController.class.getName());
conf.setInt("master.rmi.registry.port", rmiRegistryPort);
UTIL.startMiniCluster();
admin = UTIL.getConnection().getAdmin();
boolean accessDenied = false;
try {
hasAccess = false;
admin.stopMaster();
} catch (AccessDeniedException e) {


log.info("exception occurred while stopping hmaster");
}
}

};