//,temp,sample_5676.java,2,16,temp,sample_5675.java,2,16
//,2
public class xxx {
public void run() {
try {
aggregateOnTheFly();
} catch (Throwable e) {
if (e instanceof Exception) {
executionException.set((Exception) e);
} else {
executionException.set(ObjectHelper.wrapRuntimeCamelException(e));
}
} finally {


log.info("signaling we are done aggregating on the fly for exchangeid");
}
}

};