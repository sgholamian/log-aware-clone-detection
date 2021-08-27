//,temp,HostExecutor.java,447,464,temp,HostExecutor.java,415,434
//,3
public class xxx {
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

};