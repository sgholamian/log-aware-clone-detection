//,temp,RegisterEndpointTask.java,204,233,temp,HeartbeatEndpointTask.java,307,330
//,3
public class xxx {
    public RegisterEndpointTask build() {
      if (endPointStateMachine == null) {
        LOG.error("No endpoint specified.");
        throw new IllegalArgumentException("A valid endpoint state machine is" +
            " needed to construct RegisterEndPoint task");
      }

      if (conf == null) {
        LOG.error("No config specified.");
        throw new IllegalArgumentException("A valid configration is needed to" +
            " construct RegisterEndpoint task");
      }

      if (datanodeDetails == null) {
        LOG.error("No datanode specified.");
        throw new IllegalArgumentException("A vaild Node ID is needed to " +
            "construct RegisterEndpoint task");
      }

      if (container == null) {
        LOG.error("Container is not specified");
        throw new IllegalArgumentException("Container is not specified to " +
            "constrict RegisterEndpoint task");
      }

      RegisterEndpointTask task = new RegisterEndpointTask(this
          .endPointStateMachine, this.conf, this.container);
      task.setDatanodeDetails(datanodeDetails);
      return task;
    }

};