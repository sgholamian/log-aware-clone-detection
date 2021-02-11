//,temp,sample_5598.java,2,17,temp,sample_5599.java,2,17
//,2
public class xxx {
public void dummy_method(){
Path end = MRApps.getEndJobCommitSuccessFile(conf, userName, jobId);
FileSystem fs = FileSystem.get(conf);
fs.create(start).close();
fs.create(end).close();
ContainerId containerId = ContainerId.fromString(containerIdStr);
MRAppMaster appMaster = new MRAppMasterTest(applicationAttemptId, containerId, "host", -1, -1, System.currentTimeMillis(), false, false);
boolean caught = false;
try {
MRAppMaster.initAndStartAppMaster(appMaster, conf, userName);
} catch (IOException e) {


log.info("caught expected exception");
}
}

};