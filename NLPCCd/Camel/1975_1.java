//,temp,sample_1456.java,2,17,temp,sample_1454.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
lock.readLock().lock();
if (commitStrategy.commit(exchange)) {
if (LOG.isDebugEnabled()) {
}
if (session != null && session.getTransacted()) {
session.commit();
}
}
} catch (Exception e) {


log.info("failed to commit the session this exception will be ignored");
}
}

};