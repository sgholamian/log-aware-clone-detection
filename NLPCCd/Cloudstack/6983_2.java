//,temp,sample_1875.java,2,19,temp,sample_4493.java,2,19
//,3
public class xxx {
public void dummy_method(){
Account account = _accountDao.findById(accountId);
if (account != null) {
if (account.getState().equals(State.locked)) {
return true;
} else if (account.getState().equals(State.enabled)) {
AccountVO acctForUpdate = _accountDao.createForUpdate();
acctForUpdate.setState(State.locked);
success = _accountDao.update(Long.valueOf(accountId), acctForUpdate);
} else {
if (s_logger.isInfoEnabled()) {


log.info("attempting to lock a non enabled account current state is accountid locking failed");
}
}
}
}

};