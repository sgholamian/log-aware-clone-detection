//,temp,VMSanpshotOnPrimaryParser.java,103,127,temp,NetworkOfferingUsageParser.java,138,163
//,3
public class xxx {
    private static void createUsageRecord(int type, long runningTime, Date startDate, Date endDate, AccountVO account, long vmId, long noId, long zoneId,
        boolean isDefault) {
        // Our smallest increment is hourly for now
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Total running time " + runningTime + "ms");
        }

        float usage = runningTime / 1000f / 60f / 60f;

        DecimalFormat dFormat = new DecimalFormat("#.######");
        String usageDisplay = dFormat.format(usage);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Creating network offering:" + noId + " usage record for Vm : " + vmId + ", usage: " + usageDisplay + ", startDate: " + startDate +
                ", endDate: " + endDate + ", for account: " + account.getId());
        }

        // Create the usage record
        String usageDesc = "Network offering:" + noId + " for Vm : " + vmId + " usage time";

        long defaultNic = (isDefault) ? 1 : 0;
        UsageVO usageRecord =
            new UsageVO(zoneId, account.getId(), account.getDomainId(), usageDesc, usageDisplay + " Hrs", type, new Double(usage), vmId, null, noId, null, defaultNic,
                null, startDate, endDate);
        s_usageDao.persist(usageRecord);
    }

};