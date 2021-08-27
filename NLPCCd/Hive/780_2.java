//,temp,sample_4171.java,2,17,temp,sample_4170.java,2,18
//,3
public class xxx {
public void dummy_method(){
for(final Drone drone : ImmutableList.copyOf(mDrones)) {
Preconditions.checkState(remoteStagingLocation == null, "Remote staging location must be null at the start of the loop");
final Map<String, String> templateVariables = Maps.newHashMap(mTemplateDefaults);
templateVariables.put("instanceName", drone.getInstanceName());
templateVariables.put("localDir", drone.getLocalDirectory());
String resolvedRemoteLocation = Files.simplifyPath(Templates.getTemplateResult(remoteFile, templateVariables));
RSyncResult result = new RSyncCommand(mRSyncCommandExecutor, drone.getPrivateKey(), drone.getUser(), drone.getHost(), drone.getInstance(), resolvedLocalLocation, resolvedRemoteLocation, RSyncCommand.Type.FROM_LOCAL).call();
if(result.getExitCode() == Constants.EXIT_CODE_SUCCESS) {
remoteStagingLocation = resolvedRemoteLocation;
drones.remove(drone);


log.info("successfully staged on");
}
}
}

};