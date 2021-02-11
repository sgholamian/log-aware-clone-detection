//,temp,sample_5932.java,2,17,temp,sample_6008.java,2,17
//,2
public class xxx {
public void doInTransactionWithoutResult(final TransactionStatus status) {
final UserStatisticsVO stats = _userStatsDao.lock(router.getAccountId(), router.getDataCenterId(), network.getId(), forVpc ? routerNic.getIPv4Address() : null, router.getId(), routerType);
if (stats == null) {
return;
}
if (previousStats != null && (previousStats.getCurrentBytesReceived() != stats.getCurrentBytesReceived() || previousStats.getCurrentBytesSent() != stats .getCurrentBytesSent())) {
return;
}
if (stats.getCurrentBytesReceived() > answerFinal.getBytesReceived()) {
if (s_logger.isDebugEnabled()) {


log.info("received of bytes that s less than the last one assuming something went wrong and persisting it router reported stored");
}
}
}

};