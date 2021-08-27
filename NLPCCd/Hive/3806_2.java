//,temp,sample_5343.java,2,12,temp,sample_849.java,2,10
//,3
public class xxx {
public TezSessionState reopen(TezSessionState session) throws Exception {
WmTezSession wmTezSession = ensureOwnedSession(session);
HiveConf sessionConf = wmTezSession.getConf();
if (sessionConf == null) {


log.info("session configuration is null for");
}
}

};