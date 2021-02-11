//,temp,LoadBalancerUsageParser.java,58,123,temp,VolumeUsageParser.java,58,129
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing all LoadBalancerPolicy usage events for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        // - query usage_volume table with the following criteria:
        //     - look for an entry for accountId with start date in the given range
        //     - look for an entry for accountId with end date in the given range
        //     - look for an entry for accountId with end date null (currently running vm or owned IP)
        //     - look for an entry for accountId with start date before given range *and* end date after given range
        List<UsageLoadBalancerPolicyVO> usageLBs = s_usageLoadBalancerPolicyDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);

        if (usageLBs.isEmpty()) {
            s_logger.debug("No load balancer usage events for this period");
            return true;
        }

        // This map has both the running time *and* the usage amount.
        Map<String, Pair<Long, Long>> usageMap = new HashMap<String, Pair<Long, Long>>();
        Map<String, LBInfo> lbMap = new HashMap<String, LBInfo>();

        // loop through all the load balancer policies, create a usage record for each
        for (UsageLoadBalancerPolicyVO usageLB : usageLBs) {
            long lbId = usageLB.getId();
            String key = "" + lbId;

            lbMap.put(key, new LBInfo(lbId, usageLB.getZoneId()));

            Date lbCreateDate = usageLB.getCreated();
            Date lbDeleteDate = usageLB.getDeleted();

            if ((lbDeleteDate == null) || lbDeleteDate.after(endDate)) {
                lbDeleteDate = endDate;
            }

            // clip the start date to the beginning of our aggregation range if the vm has been running for a while
            if (lbCreateDate.before(startDate)) {
                lbCreateDate = startDate;
            }

            if (lbCreateDate.after(endDate)) {
                //Ignore records created after endDate
                continue;
            }

            long currentDuration = (lbDeleteDate.getTime() - lbCreateDate.getTime()) + 1; // make sure this is an inclusive check for milliseconds (i.e. use n - m + 1 to find total number of millis to charge)

            updateLBUsageData(usageMap, key, usageLB.getId(), currentDuration);
        }

        for (String lbIdKey : usageMap.keySet()) {
            Pair<Long, Long> sgtimeInfo = usageMap.get(lbIdKey);
            long useTime = sgtimeInfo.second().longValue();

            // Only create a usage record if we have a runningTime of bigger than zero.
            if (useTime > 0L) {
                LBInfo info = lbMap.get(lbIdKey);
                createUsageRecord(UsageTypes.LOAD_BALANCER_POLICY, useTime, startDate, endDate, account, info.getId(), info.getZoneId());
            }
        }

        return true;
    }

};