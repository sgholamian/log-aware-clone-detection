//,temp,sample_2830.java,2,17,temp,sample_6646.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (new File(snapshotFullOvfName).exists()) {
command = new Script(false, "cp", _timeout, s_logger);
command.add(snapshotFullOvfName);
command.add(installFullPath);
result = command.execute();
if (result != null) {
String msg = "unable to copy snapshot " + snapshotFullOvfName + " to " + installFullPath;
s_logger.error(msg);
throw new Exception(msg);
}


log.info("vmdkfile parent dir");
}
}

};