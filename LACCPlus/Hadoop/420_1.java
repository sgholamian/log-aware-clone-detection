//,temp,ContainerLauncherImpl.java,194,238,temp,ContainerLauncherImpl.java,129,192
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public synchronized void kill() {

      if(this.state == ContainerState.PREP) {
        this.state = ContainerState.KILLED_BEFORE_LAUNCH;
      } else if (!isCompletelyDone()) {
        LOG.info("KILLING " + taskAttemptID);

        ContainerManagementProtocolProxyData proxy = null;
        try {
          proxy = getCMProxy(this.containerMgrAddress, this.containerID);

          // kill the remote container if already launched
          List<ContainerId> ids = new ArrayList<ContainerId>();
          ids.add(this.containerID);
          StopContainersRequest request = StopContainersRequest.newInstance(ids);
          StopContainersResponse response =
              proxy.getContainerManagementProtocol().stopContainers(request);
          if (response.getFailedRequests() != null
              && response.getFailedRequests().containsKey(this.containerID)) {
            throw response.getFailedRequests().get(this.containerID)
              .deSerialize();
          }
        } catch (Throwable t) {
          // ignore the cleanup failure
          String message = "cleanup failed for container "
              + this.containerID + " : "
              + StringUtils.stringifyException(t);
          context.getEventHandler()
              .handle(
                  new TaskAttemptDiagnosticsUpdateEvent(this.taskAttemptID,
                      message));
          LOG.warn(message);
        } finally {
          if (proxy != null) {
            cmProxy.mayBeCloseProxy(proxy);
          }
        }
        this.state = ContainerState.DONE;
      }
      // after killing, send killed event to task attempt
      context.getEventHandler().handle(
          new TaskAttemptEvent(this.taskAttemptID,
              TaskAttemptEventType.TA_CONTAINER_CLEANED));
    }

};