//,temp,sample_1489.java,2,14,temp,sample_9563.java,2,14
//,2
public class xxx {
public static boolean parse(AccountVO account, Date startDate, Date endDate) {
if (s_logger.isDebugEnabled()) {
}
if ((endDate == null) || endDate.after(new Date())) {
endDate = new Date();
}
List<UsageNetworkOfferingVO> usageNOs = s_usageNetworkOfferingDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);
if (usageNOs.isEmpty()) {


log.info("no networkoffering usage events for this period");
}
}

};