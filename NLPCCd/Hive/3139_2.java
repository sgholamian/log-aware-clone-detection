//,temp,sample_4367.java,2,18,temp,sample_4365.java,2,18
//,2
public class xxx {
public void dummy_method(){
String confString = HiveConf.getVar(job, HiveConf.ConfVars.CLIENTSTATSPUBLISHERS);
confString = confString.trim();
if (confString.equals("")) {
return clientStatsPublishers;
}
String[] clientStatsPublisherClasses = confString.split(",");
for (String clientStatsPublisherClass : clientStatsPublisherClasses) {
try {
clientStatsPublishers.add((ClientStatsPublisher) Class.forName( clientStatsPublisherClass.trim(), true, Utilities.getSessionSpecifiedClassLoader()).newInstance());
} catch (Exception e) {


log.info("occured when trying to create class implementing clientstatspublisher interface");
}
}
}

};