//,temp,ContainerManagerImpl.java,1116,1127,temp,ContainerManagerImpl.java,1100,1111
//,3
public class xxx {
    @Override
    public void handle(ApplicationEvent event) {
      Application app =
          ContainerManagerImpl.this.context.getApplications().get(
              event.getApplicationID());
      if (app != null) {
        app.handle(event);
      } else {
        LOG.warn("Event " + event + " sent to absent application "
            + event.getApplicationID());
      }
    }

};