//,temp,sample_5225.java,2,12,temp,sample_5224.java,2,16
//,3
public class xxx {
public void testRSConnectorServerWhenStopRegionServer() throws Exception {
conf.set(CoprocessorHost.REGIONSERVER_COPROCESSOR_CONF_KEY, JMXListener.class.getName() + "," + MyAccessController.class.getName());
conf.setInt("regionserver.rmi.registry.port", rmiRegistryPort);
UTIL.startMiniCluster();
admin = UTIL.getConnection().getAdmin();
hasAccess = false;
ServerName serverName = UTIL.getHBaseCluster().getRegionServer(0).getServerName();


log.info("stopping region server");
}

};