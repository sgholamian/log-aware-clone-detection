//,temp,sample_9369.java,2,17,temp,sample_9378.java,2,17
//,3
public class xxx {
public void dummy_method(){
int jobstatus = queryAsyncJobResult(jobid);
if (jobstatus == 1) {
} else {
while (jobstatus != 1) {
DeleteVolumeResponse deleteVolumeResponse1 = deleteVolume(esvolumeid, "true");
if (deleteVolumeResponse1 != null) {
String jobid1 = deleteVolumeResponse1.getJobId();
jobstatus = queryAsyncJobResult(jobid1);
}
}


log.info("elastistor volume successfully deleted");
}
}

};