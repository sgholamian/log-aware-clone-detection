//,temp,sample_2829.java,2,16,temp,sample_6645.java,2,16
//,3
public class xxx {
public void dummy_method(){
result = command.execute();
if (result != null) {
String msg = "unable to copy snapshot " + snapshotFullOVAName + " to " + installFullPath;
s_logger.error(msg);
throw new Exception(msg);
}
command = new Script("tar", wait, s_logger);
command.add("--no-same-owner");
command.add("-xf", installFullOVAName);
command.setWorkDir(installFullPath);


log.info("executing command");
}

};