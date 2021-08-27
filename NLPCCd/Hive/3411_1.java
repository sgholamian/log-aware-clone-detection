//,temp,sample_1295.java,2,13,temp,sample_1296.java,2,14
//,2
public class xxx {
private void updateJobStateToDoneAndWriteExitValue(Configuration conf, String statusdir, String jobId, int exitCode) throws IOException {
writeExitValue(conf, exitCode, statusdir);
JobState state = new JobState(jobId, conf);
state.setExitValue(exitCode);
state.setCompleteStatus("done");
state.close();
if (exitCode != 0) {


log.info("templeton job failed with exit code");
}
}

};