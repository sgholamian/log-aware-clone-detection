//,temp,DefaultContainerExecutor.java,409,430,temp,DockerContainerExecutor.java,390,413
//,3
public class xxx {
  @Override
  public boolean signalContainer(ContainerSignalContext ctx)
      throws IOException {
    String user = ctx.getUser();
    String pid = ctx.getPid();
    Signal signal = ctx.getSignal();

    LOG.debug("Sending signal " + signal.getValue() + " to pid " + pid
        + " as user " + user);
    if (!containerIsAlive(pid)) {
      return false;
    }
    try {
      killContainer(pid, signal);
    } catch (IOException e) {
      if (!containerIsAlive(pid)) {
        return false;
      }
      throw e;
    }
    return true;
  }

};