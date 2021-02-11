//,temp,sample_4006.java,2,9,temp,sample_3330.java,2,9
//,2
public class xxx {
public ConsoleProxyInfo assignProxy(long dataCenterId, long userVmId) {
UserVmVO userVm = _userVmDao.findById(userVmId);
if (userVm == null) {


log.info("user vm no longer exists return a null proxy for user vm");
}
}

};