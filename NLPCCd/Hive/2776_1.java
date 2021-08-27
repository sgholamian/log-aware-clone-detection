//,temp,sample_5068.java,2,17,temp,sample_5067.java,2,17
//,3
public class xxx {
public void dummy_method(){
String prefix = conf.getTableInfo().getTableName().toLowerCase();
prefix = Utilities.join(prefix, spSpec, dpSpec);
prefix = prefix.endsWith(Path.SEPARATOR) ? prefix : prefix + Path.SEPARATOR;
if (Utilities.FILE_OP_LOGGER.isTraceEnabled()) {
}
Map<String, String> statsToPublish = new HashMap<String, String>();
for (String statType : fspValue.getStoredStats()) {
statsToPublish.put(statType, Long.toString(fspValue.stat.getStat(statType)));
}
if (!statsPublisher.publishStat(prefix, statsToPublish)) {


log.info("failed to publish stats");
}
}

};