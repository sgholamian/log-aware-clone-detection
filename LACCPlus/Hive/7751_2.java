//,temp,HostExecutor.java,442,468,temp,HostExecutor.java,412,437
//,3
public class xxx {
  private ListenableFuture<SSHResult> exec(final String cmd, final boolean reportErrors)
      throws Exception {
    return mExecutor.submit(new Callable<SSHResult>() {
      @Override
      public SSHResult call() throws Exception {
        for(final Drone drone : ImmutableList.copyOf(mDrones)) {
          Map<String, String> templateVariables = Maps.newHashMap(mTemplateDefaults);
          templateVariables.put("instanceName", drone.getInstanceName());
          templateVariables.put("localDir", drone.getLocalDirectory());
          String command = Templates.getTemplateResult(cmd, templateVariables);
          SSHResult result = new SSHCommand(mSSHCommandExecutor, drone.getPrivateKey(), drone.getUser(),
              drone.getHost(), drone.getInstance(), command, reportErrors).call();
          if(reportErrors && result.getExitCode() == Constants.EXIT_CODE_UNKNOWN) {
            mDrones.remove(drone); // return value not checked due to concurrent access
            mLogger.error("Aborting drone during exec " + command,
                new AbortDroneException("Drone " + drone + " exited with "
                    + Constants.EXIT_CODE_UNKNOWN + ": " + result));
          } else {
            return result;
          }
        }
        return null;
      }
    });

  }

};