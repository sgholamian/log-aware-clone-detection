//,temp,sample_6320.java,2,14,temp,sample_4210.java,2,9
//,3
public class xxx {
public String getProcessId(ContainerId containerID) {
String pid = null;
Path pidFile = pidFiles.get(containerID);
if (pidFile != null) {
try {
pid = ProcessIdFileReader.getProcessId(pidFile);
} catch (IOException e) {


log.info("got exception reading pid from pid file");
}
}
}

};