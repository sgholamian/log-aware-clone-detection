//,temp,sample_3798.java,2,17,temp,sample_702.java,2,12
//,3
public class xxx {
public void perform() throws Exception {
if (sleepTime > 0) {
Thread.sleep(sleepTime);
}
Admin admin = this.context.getHBaseIntegrationTestingUtility().getAdmin();
Collection<ServerName> serversList = admin.getClusterMetrics(EnumSet.of(Option.LIVE_SERVERS)).getLiveServerMetrics().keySet();
ServerName[] servers = serversList.toArray(new ServerName[serversList.size()]);


log.info("performing action move regions of table");
}

};