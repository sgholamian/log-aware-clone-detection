//,temp,DefaultLinuxContainerRuntime.java,114,141,temp,DockerLinuxContainerRuntime.java,239,266
//,2
public class xxx {
  @Override
  public void signalContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {
    Container container = ctx.getContainer();
    PrivilegedOperation signalOp = new PrivilegedOperation(
        PrivilegedOperation.OperationType.SIGNAL_CONTAINER, (String) null);

    signalOp.appendArgs(ctx.getExecutionAttribute(RUN_AS_USER),
        ctx.getExecutionAttribute(USER),
        Integer.toString(PrivilegedOperation.RunAsUserCommand
            .SIGNAL_CONTAINER.getValue()),
        ctx.getExecutionAttribute(PID),
        Integer.toString(ctx.getExecutionAttribute(SIGNAL).getValue()));

    try {
      PrivilegedOperationExecutor executor = PrivilegedOperationExecutor
          .getInstance(conf);

      executor.executePrivilegedOperation(null,
          signalOp, null, container.getLaunchContext().getEnvironment(),
          false);
    } catch (PrivilegedOperationException e) {
      LOG.warn("Signal container failed. Exception: ", e);

      throw new ContainerExecutionException("Signal container failed", e
          .getExitCode(), e.getOutput(), e.getErrorOutput());
    }
  }

};