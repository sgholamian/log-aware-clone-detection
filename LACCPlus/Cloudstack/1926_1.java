//,temp,VMSanpshotOnPrimaryParser.java,103,127,temp,VolumeUsageParser.java,143,173
//,3
public class xxx {
    private static void createUsageRecord(int usageType, long runningTime, Date startDate, Date endDate, AccountVO account, long vmId, String name, long zoneId, long virtualSize,
            long physicalSize) {
        // Our smallest increment is hourly for now
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Total running time " + runningTime + "ms");
        }

        float usage = runningTime / 1000f / 60f / 60f;

        DecimalFormat dFormat = new DecimalFormat("#.######");
        String usageDisplay = dFormat.format(usage);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Creating VMSnapshot On Primary usage record for vm: " + vmId + ", usage: " + usageDisplay + ", startDate: " + startDate + ", endDate: " + endDate
                    + ", for account: " + account.getId());
        }

        // Create the usage record
        String usageDesc = "VMSnapshot On Primary Usage: " + "VM Id: " + vmId;
        usageDesc += " Size: " + virtualSize;

        UsageVO usageRecord = new UsageVO(zoneId, account.getId(), account.getDomainId(), usageDesc, usageDisplay + " Hrs", usageType, new Double(usage), vmId, name, null, null,
                vmId, physicalSize, virtualSize, startDate, endDate);
        s_usageDao.persist(usageRecord);
    }

};