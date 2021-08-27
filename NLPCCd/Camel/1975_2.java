//,temp,sample_1456.java,2,17,temp,sample_1454.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
lock.readLock().lock();
if (commitStrategy.rollback(exchange)) {
if (LOG.isDebugEnabled()) {
}
if (session != null && session.getTransacted()) {
session.rollback();
}
}
} catch (Exception e) {


log.info("failed to rollback the session this exception will be ignored");
}
}

};