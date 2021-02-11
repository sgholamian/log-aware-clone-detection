//,temp,sample_9563.java,2,14,temp,sample_1064.java,2,14
//,2
public class xxx {
public static boolean parse(AccountVO account, Date startDate, Date endDate) {
if (s_logger.isDebugEnabled()) {
}
if ((endDate == null) || endDate.after(new Date())) {
endDate = new Date();
}
List<UsagePortForwardingRuleVO> usagePFs = s_usagePFRuleDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);
if (usagePFs.isEmpty()) {


log.info("no port forwarding usage events for this period");
}
}

};