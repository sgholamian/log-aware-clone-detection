//,temp,ContainerLauncherImpl.java,194,238,temp,ContainerLauncherImpl.java,129,192
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public synchronized void launch(ContainerRemoteLaunchEvent event) {
      LOG.info("Launching " + taskAttemptID);
      if(this.state == ContainerState.KILLED_BEFORE_LAUNCH) {
        state = ContainerState.DONE;
        sendContainerLaunchFailedMsg(taskAttemptID, 
            "Container was killed before it was launched");
        return;
      }
      
      ContainerManagementProtocolProxyData proxy = null;
      try {

        proxy = getCMProxy(containerMgrAddress, containerID);

        // Construct the actual Container
        ContainerLaunchContext containerLaunchContext =
          event.getContainerLaunchContext();

        // Now launch the actual container
        StartContainerRequest startRequest =
            StartContainerRequest.newInstance(containerLaunchContext,
              event.getContainerToken());
        List<StartContainerRequest> list = new ArrayList<StartContainerRequest>();
        list.add(startRequest);
        StartContainersRequest requestList = StartContainersRequest.newInstance(list);
        StartContainersResponse response =
            proxy.getContainerManagementProtocol().startContainers(requestList);
        if (response.getFailedRequests() != null
            && response.getFailedRequests().containsKey(containerID)) {
          throw response.getFailedRequests().get(containerID).deSerialize();
        }
        ByteBuffer portInfo =
            response.getAllServicesMetaData().get(
                ShuffleHandler.MAPREDUCE_SHUFFLE_SERVICEID);
        int port = -1;
        if(portInfo != null) {
          port = ShuffleHandler.deserializeMetaData(portInfo);
        }
        LOG.info("Shuffle port returned by ContainerManager for "
            + taskAttemptID + " : " + port);

        if(port < 0) {
          this.state = ContainerState.FAILED;
          throw new IllegalStateException("Invalid shuffle port number "
              + port + " returned for " + taskAttemptID);
        }

        // after launching, send launched event to task attempt to move
        // it from ASSIGNED to RUNNING state
        context.getEventHandler().handle(
            new TaskAttemptContainerLaunchedEvent(taskAttemptID, port));
        this.state = ContainerState.RUNNING;
      } catch (Throwable t) {
        String message = "Container launch failed for " + containerID + " : "
            + StringUtils.stringifyException(t);
        this.state = ContainerState.FAILED;
        sendContainerLaunchFailedMsg(taskAttemptID, message);
      } finally {
        if (proxy != null) {
          cmProxy.mayBeCloseProxy(proxy);
        }
      }
    }

};