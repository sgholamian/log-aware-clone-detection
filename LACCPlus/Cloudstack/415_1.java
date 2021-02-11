//,temp,SecurityGroupUsageParser.java,138,161,temp,VolumeUsageParser.java,143,173
//,3
public class xxx {
    private static void createUsageRecord(int type, long runningTime, Date startDate, Date endDate, AccountVO account, long vmId, long sgId, long zoneId) {
        // Our smallest increment is hourly for now
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Total running time " + runningTime + "ms");
        }

        float usage = runningTime / 1000f / 60f / 60f;

        DecimalFormat dFormat = new DecimalFormat("#.######");
        String usageDisplay = dFormat.format(usage);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Creating security group:" + sgId + " usage record for Vm : " + vmId + ", usage: " + usageDisplay + ", startDate: " + startDate +
                ", endDate: " + endDate + ", for account: " + account.getId());
        }

        // Create the usage record
        String usageDesc = "Security Group: " + sgId + " for Vm : " + vmId + " usage time";

        UsageVO usageRecord =
            new UsageVO(zoneId, account.getId(), account.getDomainId(), usageDesc, usageDisplay + " Hrs", type, new Double(usage), vmId, null, null, null, sgId, null,
                startDate, endDate);
        s_usageDao.persist(usageRecord);
    }

};