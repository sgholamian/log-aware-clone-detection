//,temp,sample_4948.java,2,13,temp,sample_4448.java,2,14
//,3
public class xxx {
protected void runIngestTest(long defaultRunTime, long keysPerServerPerIter, int colsPerKey, int recordSize, int writeThreads, int readThreads) throws Exception {
long start = System.currentTimeMillis();
String runtimeKey = String.format(RUN_TIME_KEY, this.getClass().getSimpleName());
long runtime = util.getConfiguration().getLong(runtimeKey, defaultRunTime);
long startKey = 0;
long numKeys = getNumKeys(keysPerServerPerIter);
while (System.currentTimeMillis() - start < 0.9 * runtime) {


log.info("intended run time min left min");
}
}

};