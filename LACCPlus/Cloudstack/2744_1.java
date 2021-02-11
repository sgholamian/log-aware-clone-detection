//,temp,SecurityGroupUsageParser.java,58,124,temp,VPNUserUsageParser.java,58,119
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing all SecurityGroup usage events for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        // - query usage_volume table with the following criteria:
        //     - look for an entry for accountId with start date in the given range
        //     - look for an entry for accountId with end date in the given range
        //     - look for an entry for accountId with end date null (currently running vm or owned IP)
        //     - look for an entry for accountId with start date before given range *and* end date after given range
        List<UsageSecurityGroupVO> usageSGs = s_usageSecurityGroupDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);

        if (usageSGs.isEmpty()) {
            s_logger.debug("No SecurityGroup usage events for this period");
            return true;
        }

        // This map has both the running time *and* the usage amount.
        Map<String, Pair<Long, Long>> usageMap = new HashMap<String, Pair<Long, Long>>();
        Map<String, SGInfo> sgMap = new HashMap<String, SGInfo>();

        // loop through all the security groups, create a usage record for each
        for (UsageSecurityGroupVO usageSG : usageSGs) {
            long vmId = usageSG.getVmInstanceId();
            long sgId = usageSG.getSecurityGroupId();
            String key = "" + vmId + "SG" + sgId;

            sgMap.put(key, new SGInfo(vmId, usageSG.getZoneId(), sgId));

            Date sgCreateDate = usageSG.getCreated();
            Date sgDeleteDate = usageSG.getDeleted();

            if ((sgDeleteDate == null) || sgDeleteDate.after(endDate)) {
                sgDeleteDate = endDate;
            }

            // clip the start date to the beginning of our aggregation range if the vm has been running for a while
            if (sgCreateDate.before(startDate)) {
                sgCreateDate = startDate;
            }

            if (sgCreateDate.after(endDate)) {
                //Ignore records created after endDate
                continue;
            }

            long currentDuration = (sgDeleteDate.getTime() - sgCreateDate.getTime()) + 1; // make sure this is an inclusive check for milliseconds (i.e. use n - m + 1 to find total number of millis to charge)

            updateSGUsageData(usageMap, key, usageSG.getVmInstanceId(), currentDuration);
        }

        for (String sgIdKey : usageMap.keySet()) {
            Pair<Long, Long> sgtimeInfo = usageMap.get(sgIdKey);
            long useTime = sgtimeInfo.second().longValue();

            // Only create a usage record if we have a runningTime of bigger than zero.
            if (useTime > 0L) {
                SGInfo info = sgMap.get(sgIdKey);
                createUsageRecord(UsageTypes.SECURITY_GROUP, useTime, startDate, endDate, account, info.getVmId(), info.getSGId(), info.getZoneId());
            }
        }

        return true;
    }

};