//,temp,ApplicationImpl.java,561,578,temp,ApplicationImpl.java,504,515
//,3
public class xxx {
    @Override
    public ApplicationState transition(ApplicationImpl app,
        ApplicationEvent event) {

      ApplicationContainerFinishedEvent containerFinishEvent =
          (ApplicationContainerFinishedEvent) event;
      LOG.info("Removing " + containerFinishEvent.getContainerID()
          + " from application " + app.toString());
      app.containers.remove(containerFinishEvent.getContainerID());

      if (app.containers.isEmpty()) {
        // All containers are cleanedup.
        app.handleAppFinishWithContainersCleanedup();
        return ApplicationState.APPLICATION_RESOURCES_CLEANINGUP;
      }

      return ApplicationState.FINISHING_CONTAINERS_WAIT;
    }

};