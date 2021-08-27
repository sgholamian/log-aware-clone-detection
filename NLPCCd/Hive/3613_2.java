//,temp,sample_2626.java,3,14,temp,sample_2632.java,2,15
//,3
public class xxx {
public void close(boolean abort) throws HiveException {
if (LOG.isDebugEnabled()) {
}
if (state == State.CLOSE) {
return;
}
if (!allInitializedParentsAreClosed()) {
if (LOG.isDebugEnabled()) {


log.info("not all parent operators are closed not closing");
}
}
}

};