//,temp,LoadBalancerUsageParser.java,137,161,temp,SecurityGroupUsageParser.java,138,161
//,3
public class xxx {
    private static void createUsageRecord(int type, long runningTime, Date startDate, Date endDate, AccountVO account, long lbId, long zoneId) {
        // Our smallest increment is hourly for now
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Total running time " + runningTime + "ms");
        }

        float usage = runningTime / 1000f / 60f / 60f;

        DecimalFormat dFormat = new DecimalFormat("#.######");
        String usageDisplay = dFormat.format(usage);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Creating Volume usage record for load balancer: " + lbId + ", usage: " + usageDisplay + ", startDate: " + startDate + ", endDate: " +
                endDate + ", for account: " + account.getId());
        }

        // Create the usage record
        String usageDesc = "Load Balancing Policy: " + lbId + " usage time";

        //ToDo: get zone id
        UsageVO usageRecord =
            new UsageVO(zoneId, account.getId(), account.getDomainId(), usageDesc, usageDisplay + " Hrs", type, new Double(usage), null, null, null, null, lbId, null,
                startDate, endDate);
        s_usageDao.persist(usageRecord);
    }

};