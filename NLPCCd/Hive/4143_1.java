//,temp,sample_5066.java,2,17,temp,sample_5042.java,2,14
//,3
public class xxx {
public void dummy_method(){
StatsPublisher statsPublisher = Utilities.getStatsPublisher(jc);
if (statsPublisher == null) {
if (isStatsReliable) {
throw new HiveException(ErrorMsg.STATSPUBLISHER_NOT_OBTAINED.getErrorCodedMsg());
}
return;
}
StatsCollectionContext sContext = new StatsCollectionContext(hconf);
sContext.setStatsTmpDir(conf.getTmpStatsDir());
if (!statsPublisher.connect(sContext)) {


log.info("statspublishing error cannot connect to database");
}
}

};