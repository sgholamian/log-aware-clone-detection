//,temp,IPAddressUsageParser.java,58,126,temp,VolumeUsageParser.java,58,129
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing IP Address usage for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        // - query usage_ip_address table with the following criteria:
        //     - look for an entry for accountId with start date in the given range
        //     - look for an entry for accountId with end date in the given range
        //     - look for an entry for accountId with end date null (currently running vm or owned IP)
        //     - look for an entry for accountId with start date before given range *and* end date after given range
        List<UsageIPAddressVO> usageIPAddress = s_usageIPAddressDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate);

        if (usageIPAddress.isEmpty()) {
            s_logger.debug("No IP Address usage for this period");
            return true;
        }

        // This map has both the running time *and* the usage amount.
        Map<String, Pair<Long, Long>> usageMap = new HashMap<String, Pair<Long, Long>>();

        Map<String, IpInfo> IPMap = new HashMap<String, IpInfo>();

        // loop through all the usage IPs, create a usage record for each
        for (UsageIPAddressVO usageIp : usageIPAddress) {
            long IpId = usageIp.getId();

            String key = "" + IpId;

            // store the info in the IP map
            IPMap.put(key, new IpInfo(usageIp.getZoneId(), IpId, usageIp.getAddress(), usageIp.isSourceNat(), usageIp.isSystem()));

            Date IpAssignDate = usageIp.getAssigned();
            Date IpReleaseDeleteDate = usageIp.getReleased();

            if ((IpReleaseDeleteDate == null) || IpReleaseDeleteDate.after(endDate)) {
                IpReleaseDeleteDate = endDate;
            }

            // clip the start date to the beginning of our aggregation range if the vm has been running for a while
            if (IpAssignDate.before(startDate)) {
                IpAssignDate = startDate;
            }

            if (IpAssignDate.after(endDate)) {
                //Ignore records created after endDate
                continue;
            }

            long currentDuration = (IpReleaseDeleteDate.getTime() - IpAssignDate.getTime()) + 1; // make sure this is an inclusive check for milliseconds (i.e. use n - m + 1 to find total number of millis to charge)

            updateIpUsageData(usageMap, key, usageIp.getId(), currentDuration);
        }

        for (String ipIdKey : usageMap.keySet()) {
            Pair<Long, Long> ipTimeInfo = usageMap.get(ipIdKey);
            long useTime = ipTimeInfo.second().longValue();

            // Only create a usage record if we have a runningTime of bigger than zero.
            if (useTime > 0L) {
                IpInfo info = IPMap.get(ipIdKey);
                createUsageRecord(info.getZoneId(), useTime, startDate, endDate, account, info.getIpId(), info.getIPAddress(), info.isSourceNat(), info.isSystem);
            }
        }

        return true;
    }

};