//,temp,sample_232.java,2,17,temp,sample_2032.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (ip != null && nic.getReservationStrategy() != Nic.ReservationStrategy.Managed) {
Transaction.execute(new TransactionCallbackNoReturn() {
public void doInTransactionWithoutResult(TransactionStatus status) {
_ipAddrMgr.markIpAsUnavailable(ip.getId());
_ipAddressDao.unassignIpAddress(ip.getId());
}
});
}
nic.deallocate();
if (s_logger.isDebugEnabled()) {


log.info("deallocated nic");
}
}

};