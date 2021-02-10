//,temp,ApplicationImpl.java,561,578,temp,ApplicationImpl.java,504,515
//,3
public class xxx {
    @Override
    public void transition(ApplicationImpl app, ApplicationEvent event) {
      ApplicationContainerFinishedEvent containerEvent =
          (ApplicationContainerFinishedEvent) event;
      if (null == app.containers.remove(containerEvent.getContainerID())) {
        LOG.warn("Removing unknown " + containerEvent.getContainerID() +
            " from application " + app.toString());
      } else {
        LOG.info("Removing " + containerEvent.getContainerID() +
            " from application " + app.toString());
      }
    }

};