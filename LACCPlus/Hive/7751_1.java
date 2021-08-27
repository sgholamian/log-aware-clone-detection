//,temp,HostExecutor.java,442,468,temp,HostExecutor.java,412,437
//,3
public class xxx {
  private List<ListenableFuture<RemoteCommandResult>> execInstances(List<Drone> drones, final String cmd)
      throws InterruptedException, IOException {
    List<ListenableFuture<RemoteCommandResult>> result = Lists.newArrayList();
    for(final Drone drone : ImmutableList.copyOf(drones)) {
      result.add(mExecutor.submit(new Callable<RemoteCommandResult>() {
        @Override
        public RemoteCommandResult call() throws Exception {
          Map<String, String> templateVariables = Maps.newHashMap(mTemplateDefaults);
          templateVariables.put("instanceName", drone.getInstanceName());
          templateVariables.put("localDir", drone.getLocalDirectory());
          String command = Templates.getTemplateResult(cmd, templateVariables);
          SSHResult result = new SSHCommand(mSSHCommandExecutor, drone.getPrivateKey(), drone.getUser(),
              drone.getHost(), drone.getInstance(), command, true).call();
          if(result.getExitCode() != Constants.EXIT_CODE_SUCCESS) {
            mDrones.remove(drone); // return value not checked due to concurrent access
            mLogger.error("Aborting drone during exec " + command,
                new AbortDroneException("Drone " + drone + " exited with "
                    + result.getExitCode() + ": " + result));
            return null;
          } else {
            return result;
          }
        }
      }));
    }
    return result;
  }

};