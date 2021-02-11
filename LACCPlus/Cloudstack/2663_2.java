//,temp,LoadBalancerUsageParser.java,137,161,temp,VMSnapshotUsageParser.java,122,152
//,3
public class xxx {
    private static void createUsageRecord(int type, long runningTime, Date startDate, Date endDate, AccountVO account, long volId, long zoneId, Long doId, Long vmId,
        long size) {
        // Our smallest increment is hourly for now
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Total running time " + runningTime + "ms");
        }

        float usage = runningTime / 1000f / 60f / 60f;

        DecimalFormat dFormat = new DecimalFormat("#.######");
        String usageDisplay = dFormat.format(usage);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Creating VMSnapshot Volume usage record for vol: " + volId + ", usage: " + usageDisplay + ", startDate: " + startDate + ", endDate: " +
                endDate + ", for account: " + account.getId());
        }

        // Create the usage record
        String usageDesc = "VMSnapshot Usage: " + "VM Id: " + vmId + " Volume Id: " + volId + " ";

        if (doId != null) {
            usageDesc += " DiskOffering: " + doId;
        }

        usageDesc += " Size: " + size;

        UsageVO usageRecord =
            new UsageVO(zoneId, account.getId(), account.getDomainId(), usageDesc, usageDisplay + " Hrs", type, new Double(usage), vmId, null, doId, null, volId, size,
                startDate, endDate);
        s_usageDao.persist(usageRecord);
    }

};