//,temp,StorageUsageParser.java,59,133,temp,VolumeUsageParser.java,58,129
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing all Storage usage events for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        // - query usage_volume table with the following criteria:
        //     - look for an entry for accountId with start date in the given range
        //     - look for an entry for accountId with end date in the given range
        //     - look for an entry for accountId with end date null (currently running vm or owned IP)
        //     - look for an entry for accountId with start date before given range *and* end date after given range
        List<UsageStorageVO> usageUsageStorages = s_usageStorageDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);

        if (usageUsageStorages.isEmpty()) {
            s_logger.debug("No Storage usage events for this period");
            return true;
        }

        // This map has both the running time *and* the usage amount.
        Map<String, Pair<Long, Long>> usageMap = new HashMap<String, Pair<Long, Long>>();

        Map<String, StorageInfo> storageMap = new HashMap<String, StorageInfo>();

        // loop through all the usage volumes, create a usage record for each
        for (UsageStorageVO usageStorage : usageUsageStorages) {
            long storageId = usageStorage.getId();
            int storage_type = usageStorage.getStorageType();
            long size = usageStorage.getSize();
            Long virtualSize = usageStorage.getVirtualSize();
            long zoneId = usageStorage.getZoneId();
            Long sourceId = usageStorage.getSourceId();

            String key = "" + storageId + "Z" + zoneId + "T" + storage_type;

            // store the info in the storage map
            storageMap.put(key, new StorageInfo(zoneId, storageId, storage_type, sourceId, size, virtualSize));

            Date storageCreateDate = usageStorage.getCreated();
            Date storageDeleteDate = usageStorage.getDeleted();

            if ((storageDeleteDate == null) || storageDeleteDate.after(endDate)) {
                storageDeleteDate = endDate;
            }

            // clip the start date to the beginning of our aggregation range if the vm has been running for a while
            if (storageCreateDate.before(startDate)) {
                storageCreateDate = startDate;
            }

            if (storageCreateDate.after(endDate)) {
                //Ignore records created after endDate
                continue;
            }

            long currentDuration = (storageDeleteDate.getTime() - storageCreateDate.getTime()) + 1; // make sure this is an inclusive check for milliseconds (i.e. use n - m + 1 to find total number of millis to charge)

            updateStorageUsageData(usageMap, key, usageStorage.getId(), currentDuration);
        }

        for (String storageIdKey : usageMap.keySet()) {
            Pair<Long, Long> storagetimeInfo = usageMap.get(storageIdKey);
            long useTime = storagetimeInfo.second().longValue();

            // Only create a usage record if we have a runningTime of bigger than zero.
            if (useTime > 0L) {
                StorageInfo info = storageMap.get(storageIdKey);
                createUsageRecord(info.getZoneId(), info.getStorageType(), useTime, startDate, endDate, account, info.getStorageId(), info.getSourceId(), info.getSize(),
                    info.getVirtualSize());
            }
        }

        return true;
    }

};