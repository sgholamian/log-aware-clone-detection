//,temp,sample_5033.java,2,12,temp,sample_278.java,2,8
//,3
public class xxx {
public SignalContainerResponse signalToContainer( SignalContainerRequest request) throws YarnException, IOException {
ContainerId containerId = request.getContainerId();
UserGroupInformation callerUGI;
try {
callerUGI = UserGroupInformation.getCurrentUser();
} catch (IOException ie) {


log.info("error getting ugi");
}
}

};