//,temp,sample_6884.java,2,18,temp,sample_6920.java,2,18
//,2
public class xxx {
public void dummy_method(){
long proxyVmId = 0;
try {
proxyVmId = Long.parseLong(tokens[1]);
} catch (NumberFormatException e) {
return;
}
final ConsoleProxyVO proxy = _consoleProxyDao.findById(proxyVmId);
if (proxy != null) {
} else {
if (s_logger.isInfoEnabled()) {


log.info("console proxy agent disconnected but corresponding console proxy vm no longer exists in db proxy");
}
}
}

};