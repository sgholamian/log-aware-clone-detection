//,temp,sample_1341.java,2,14,temp,sample_1011.java,2,15
//,3
public class xxx {
protected void closeOp(boolean abort) throws HiveException {
if (!abort && reducerHash != null) {
reducerHash.flush();
}
super.closeOp(abort);
out = null;
random = null;
reducerHash = null;
if (LOG.isTraceEnabled()) {


log.info("records written");
}
}

};