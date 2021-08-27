//,temp,sample_3997.java,2,15,temp,sample_3996.java,2,13
//,3
public class xxx {
public void abort() {
RecordProcessor rProcLocal;
synchronized (this) {
aborted.set(true);
rProcLocal = rproc;
}
if (rProcLocal != null) {
rProcLocal.abort();
} else {


log.info("recordprocessor not yet setup abort will be ignored");
}
}

};