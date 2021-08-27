//,temp,sample_191.java,2,10,temp,sample_3863.java,2,9
//,3
public class xxx {
public synchronized void onTaskEnd(SparkListenerTaskEnd taskEnd) {
int stageId = taskEnd.stageId();
Integer jobId = stageIdToJobId.get(stageId);
if (jobId == null) {


log.info("can not find job id for stage");
}
}

};