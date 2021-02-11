//,temp,VMSanpshotOnPrimaryParser.java,57,101,temp,VPNUserUsageParser.java,58,119
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing all VPN user usage events for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        List<UsageVPNUserVO> usageVUs = s_usageVPNUserDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);

        if (usageVUs.isEmpty()) {
            s_logger.debug("No VPN user usage events for this period");
            return true;
        }

        // This map has both the running time *and* the usage amount.
        Map<String, Pair<Long, Long>> usageMap = new HashMap<String, Pair<Long, Long>>();
        Map<String, VUInfo> vuMap = new HashMap<String, VUInfo>();

        // loop through all the VPN user usage, create a usage record for each
        for (UsageVPNUserVO usageVU : usageVUs) {
            long userId = usageVU.getUserId();
            String userName = usageVU.getUsername();
            String key = "" + userId + "VU" + userName;

            vuMap.put(key, new VUInfo(userId, usageVU.getZoneId(), userName));

            Date vuCreateDate = usageVU.getCreated();
            Date vuDeleteDate = usageVU.getDeleted();

            if ((vuDeleteDate == null) || vuDeleteDate.after(endDate)) {
                vuDeleteDate = endDate;
            }

            // clip the start date to the beginning of our aggregation range if the vm has been running for a while
            if (vuCreateDate.before(startDate)) {
                vuCreateDate = startDate;
            }

            if (vuCreateDate.after(endDate)) {
                //Ignore records created after endDate
                continue;
            }

            long currentDuration = (vuDeleteDate.getTime() - vuCreateDate.getTime()) + 1; // make sure this is an inclusive check for milliseconds (i.e. use n - m + 1 to find total number of millis to charge)

            updateVUUsageData(usageMap, key, usageVU.getUserId(), currentDuration);
        }

        for (String vuIdKey : usageMap.keySet()) {
            Pair<Long, Long> vutimeInfo = usageMap.get(vuIdKey);
            long useTime = vutimeInfo.second().longValue();

            // Only create a usage record if we have a runningTime of bigger than zero.
            if (useTime > 0L) {
                VUInfo info = vuMap.get(vuIdKey);
                createUsageRecord(UsageTypes.VPN_USERS, useTime, startDate, endDate, account, info.getUserId(), info.getUserName(), info.getZoneId());
            }
        }

        return true;
    }

};