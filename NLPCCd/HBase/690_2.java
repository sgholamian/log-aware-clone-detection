//,temp,sample_4948.java,2,13,temp,sample_1017.java,2,11
//,3
public class xxx {
protected void runIngestTest(long defaultRunTime, long keysPerServerPerIter, int colsPerKey, int recordSize, int writeThreads, int readThreads) throws Exception {
long start = System.currentTimeMillis();
String runtimeKey = String.format(RUN_TIME_KEY, this.getClass().getSimpleName());
long runtime = util.getConfiguration().getLong(runtimeKey, defaultRunTime);
long startKey = 0;
long numKeys = getNumKeys(keysPerServerPerIter);


log.info("writing some data to the table");
}

};