//,temp,sample_9563.java,2,14,temp,sample_4764.java,2,14
//,2
public class xxx {
public static boolean parse(AccountVO account, Date startDate, Date endDate) {
if (s_logger.isDebugEnabled()) {
}
if ((endDate == null) || endDate.after(new Date())) {
endDate = new Date();
}
List<UsageVPNUserVO> usageVUs = s_usageVPNUserDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate, false, 0);
if (usageVUs.isEmpty()) {


log.info("no vpn user usage events for this period");
}
}

};