//,temp,sample_3799.java,2,13,temp,sample_1140.java,2,7
//,3
public class xxx {
private void reportNewCollectorInfoToNM(ApplicationId appId, org.apache.hadoop.yarn.api.records.Token token) throws YarnException, IOException {
ReportNewCollectorInfoRequest request = ReportNewCollectorInfoRequest.newInstance(appId, this.timelineRestServerBindAddress, token);


log.info("report a new collector for application to the nm collector service");
}

};