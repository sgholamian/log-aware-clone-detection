//,temp,PortForwardingUsageParser.java,58,123,temp,VPNUserUsageParser.java,58,119
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing all PortForwardingRule usage events for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        // - query usage_volume table with the following criteria:
        //     - look for an entry for accountId with start date in the given range
        //     - look for an entry for accountId with end date in the given range
        //     - look for an entry for accountId with end date null (currently running vm or owned IP)
        //     - look for an entry for accountId with start date before given range *and* end date after given range
        List<UsagePortForwardingRuleVO> usagePFs = s_usagePFRuleDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);

        if (usagePFs.isEmpty()) {
            s_logger.debug("No port forwarding usage events for this period");
            return true;
        }

        // This map has both the running time *and* the usage amount.
        Map<String, Pair<Long, Long>> usageMap = new HashMap<String, Pair<Long, Long>>();
        Map<String, PFInfo> pfMap = new HashMap<String, PFInfo>();

        // loop through all the port forwarding rule, create a usage record for each
        for (UsagePortForwardingRuleVO usagePF : usagePFs) {
            long pfId = usagePF.getId();
            String key = "" + pfId;

            pfMap.put(key, new PFInfo(pfId, usagePF.getZoneId()));

            Date pfCreateDate = usagePF.getCreated();
            Date pfDeleteDate = usagePF.getDeleted();

            if ((pfDeleteDate == null) || pfDeleteDate.after(endDate)) {
                pfDeleteDate = endDate;
            }

            // clip the start date to the beginning of our aggregation range if the vm has been running for a while
            if (pfCreateDate.before(startDate)) {
                pfCreateDate = startDate;
            }

            if (pfCreateDate.after(endDate)) {
                //Ignore records created after endDate
                continue;
            }

            long currentDuration = (pfDeleteDate.getTime() - pfCreateDate.getTime()) + 1; // make sure this is an inclusive check for milliseconds (i.e. use n - m + 1 to find total number of millis to charge)

            updatePFUsageData(usageMap, key, usagePF.getId(), currentDuration);
        }

        for (String pfIdKey : usageMap.keySet()) {
            Pair<Long, Long> sgtimeInfo = usageMap.get(pfIdKey);
            long useTime = sgtimeInfo.second().longValue();

            // Only create a usage record if we have a runningTime of bigger than zero.
            if (useTime > 0L) {
                PFInfo info = pfMap.get(pfIdKey);
                createUsageRecord(UsageTypes.PORT_FORWARDING_RULE, useTime, startDate, endDate, account, info.getId(), info.getZoneId());
            }
        }

        return true;
    }

};