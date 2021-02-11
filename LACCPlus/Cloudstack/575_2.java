//,temp,PortForwardingUsageParser.java,58,123,temp,VolumeUsageParser.java,58,129
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing all Volume usage events for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        // - query usage_volume table with the following criteria:
        //     - look for an entry for accountId with start date in the given range
        //     - look for an entry for accountId with end date in the given range
        //     - look for an entry for accountId with end date null (currently running vm or owned IP)
        //     - look for an entry for accountId with start date before given range *and* end date after given range
        List<UsageVolumeVO> usageUsageVols = s_usageVolumeDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);

        if (usageUsageVols.isEmpty()) {
            s_logger.debug("No volume usage events for this period");
            return true;
        }

        // This map has both the running time *and* the usage amount.
        Map<String, Pair<Long, Long>> usageMap = new HashMap<String, Pair<Long, Long>>();

        Map<String, VolInfo> diskOfferingMap = new HashMap<String, VolInfo>();

        // loop through all the usage volumes, create a usage record for each
        for (UsageVolumeVO usageVol : usageUsageVols) {
            long volId = usageVol.getId();
            Long doId = usageVol.getDiskOfferingId();
            long zoneId = usageVol.getZoneId();
            Long templateId = usageVol.getTemplateId();
            long size = usageVol.getSize();
            String key = volId + "-" + doId + "-" + size;

            diskOfferingMap.put(key, new VolInfo(volId, zoneId, doId, templateId, size));

            Date volCreateDate = usageVol.getCreated();
            Date volDeleteDate = usageVol.getDeleted();

            if ((volDeleteDate == null) || volDeleteDate.after(endDate)) {
                volDeleteDate = endDate;
            }

            // clip the start date to the beginning of our aggregation range if the vm has been running for a while
            if (volCreateDate.before(startDate)) {
                volCreateDate = startDate;
            }

            if (volCreateDate.after(endDate)) {
                //Ignore records created after endDate
                continue;
            }

            long currentDuration = (volDeleteDate.getTime() - volCreateDate.getTime()) + 1; // make sure this is an inclusive check for milliseconds (i.e. use n - m + 1 to find total number of millis to charge)

            updateVolUsageData(usageMap, key, usageVol.getId(), currentDuration);
        }

        for (String volIdKey : usageMap.keySet()) {
            Pair<Long, Long> voltimeInfo = usageMap.get(volIdKey);
            long useTime = voltimeInfo.second().longValue();

            // Only create a usage record if we have a runningTime of bigger than zero.
            if (useTime > 0L) {
                VolInfo info = diskOfferingMap.get(volIdKey);
                createUsageRecord(UsageTypes.VOLUME, useTime, startDate, endDate, account, info.getVolumeId(), info.getZoneId(), info.getDiskOfferingId(),
                    info.getTemplateId(), info.getSize());
            }
        }

        return true;
    }

};