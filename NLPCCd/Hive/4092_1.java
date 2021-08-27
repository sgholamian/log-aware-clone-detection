//,temp,sample_1759.java,2,15,temp,sample_1760.java,2,19
//,3
public class xxx {
private void startInitialSession(SessionType session) throws Exception {
boolean isUsable = session.tryUse(true);
if (!isUsable) throw new IOException(session + " is not usable at pool startup");
session.getConf().set(TezConfiguration.TEZ_QUEUE_NAME, session.getQueueName());
configureAmRegistry(session);
session.open();
if (session.stopUsing()) {
if (!putSessionBack(session, false)) {


log.info("couldn t add a session during initialization");
}
}
}

};