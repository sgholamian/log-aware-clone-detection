//,temp,SecurityGroupUsageParser.java,58,124,temp,NetworkOfferingUsageParser.java,58,124
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing all NetworkOffering usage events for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        // - query usage_volume table with the following criteria:
        //     - look for an entry for accountId with start date in the given range
        //     - look for an entry for accountId with end date in the given range
        //     - look for an entry for accountId with end date null (currently running vm or owned IP)
        //     - look for an entry for accountId with start date before given range *and* end date after given range
        List<UsageNetworkOfferingVO> usageNOs = s_usageNetworkOfferingDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);

        if (usageNOs.isEmpty()) {
            s_logger.debug("No NetworkOffering usage events for this period");
            return true;
        }

        // This map has both the running time *and* the usage amount.
        Map<String, Pair<Long, Long>> usageMap = new HashMap<String, Pair<Long, Long>>();
        Map<String, NOInfo> noMap = new HashMap<String, NOInfo>();

        // loop through all the network offerings, create a usage record for each
        for (UsageNetworkOfferingVO usageNO : usageNOs) {
            long vmId = usageNO.getVmInstanceId();
            long noId = usageNO.getNetworkOfferingId();
            String key = "" + vmId + "NO" + noId;

            noMap.put(key, new NOInfo(vmId, usageNO.getZoneId(), noId, usageNO.isDefault()));

            Date noCreateDate = usageNO.getCreated();
            Date noDeleteDate = usageNO.getDeleted();

            if ((noDeleteDate == null) || noDeleteDate.after(endDate)) {
                noDeleteDate = endDate;
            }

            // clip the start date to the beginning of our aggregation range if the vm has been running for a while
            if (noCreateDate.before(startDate)) {
                noCreateDate = startDate;
            }

            if (noCreateDate.after(endDate)) {
                //Ignore records created after endDate
                continue;
            }

            long currentDuration = (noDeleteDate.getTime() - noCreateDate.getTime()) + 1; // make sure this is an inclusive check for milliseconds (i.e. use n - m + 1 to find total number of millis to charge)

            updateNOUsageData(usageMap, key, usageNO.getVmInstanceId(), currentDuration);
        }

        for (String noIdKey : usageMap.keySet()) {
            Pair<Long, Long> notimeInfo = usageMap.get(noIdKey);
            long useTime = notimeInfo.second().longValue();

            // Only create a usage record if we have a runningTime of bigger than zero.
            if (useTime > 0L) {
                NOInfo info = noMap.get(noIdKey);
                createUsageRecord(UsageTypes.NETWORK_OFFERING, useTime, startDate, endDate, account, info.getVmId(), info.getNOId(), info.getZoneId(), info.isDefault());
            }
        }

        return true;
    }

};