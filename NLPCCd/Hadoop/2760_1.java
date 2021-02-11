//,temp,sample_9459.java,2,12,temp,sample_9458.java,2,12
//,2
public class xxx {
protected synchronized void serviceStart() throws Exception {
appHistoryServer.start();
if (appHistoryServer.getServiceState() != STATE.STARTED) {
IOException ioe = new IOException( "ApplicationHistoryServer failed to start. Final state is " + appHistoryServer.getServiceState());
ioe.initCause(appHistoryServer.getFailureCause());
throw ioe;
}


log.info("miniyarn applicationhistoryserver web address");
}

};