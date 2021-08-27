//,temp,sample_5066.java,2,17,temp,sample_5042.java,2,14
//,3
public class xxx {
private void publishStats() throws HiveException {
boolean isStatsReliable = conf.isStatsReliable();
StatsPublisher statsPublisher = Utilities.getStatsPublisher(jc);
StatsCollectionContext sc = new StatsCollectionContext(jc);
sc.setStatsTmpDir(conf.getTmpStatsDir());
if (!statsPublisher.connect(sc)) {
if (LOG.isInfoEnabled()) {


log.info("statspublishing error cannot connect to database");
}
}
}

};