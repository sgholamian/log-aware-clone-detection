//,temp,sample_703.java,2,14,temp,sample_4214.java,2,14
//,2
public class xxx {
public static boolean parse(AccountVO account, Date startDate, Date endDate) {
if (s_logger.isDebugEnabled()) {
}
if ((endDate == null) || endDate.after(new Date())) {
endDate = new Date();
}
List<UsageStorageVO> usageUsageStorages = s_usageStorageDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);
if (usageUsageStorages.isEmpty()) {


log.info("no storage usage events for this period");
}
}

};