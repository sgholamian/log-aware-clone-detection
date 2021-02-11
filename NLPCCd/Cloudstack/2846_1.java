//,temp,sample_6752.java,2,13,temp,sample_1491.java,2,13
//,3
public class xxx {
private static void createUsageRecord(int type, long runningTime, Date startDate, Date endDate, AccountVO account, long volId, long zoneId, Long doId, Long vmId, long size) {
if (s_logger.isDebugEnabled()) {
}
float usage = runningTime / 1000f / 60f / 60f;
DecimalFormat dFormat = new DecimalFormat("#.######");
String usageDisplay = dFormat.format(usage);
if (s_logger.isDebugEnabled()) {


log.info("creating vmsnapshot volume usage record for vol usage startdate enddate for account");
}
}

};