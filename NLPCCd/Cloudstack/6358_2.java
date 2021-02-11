//,temp,sample_1066.java,2,13,temp,sample_4766.java,2,13
//,3
public class xxx {
private static void createUsageRecord(int type, long runningTime, Date startDate, Date endDate, AccountVO account, long vmId, long sgId, long zoneId) {
if (s_logger.isDebugEnabled()) {
}
float usage = runningTime / 1000f / 60f / 60f;
DecimalFormat dFormat = new DecimalFormat("#.######");
String usageDisplay = dFormat.format(usage);
if (s_logger.isDebugEnabled()) {


log.info("creating security group usage record for vm usage startdate enddate for account");
}
}

};