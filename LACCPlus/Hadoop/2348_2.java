//,temp,RegisterEndpointTask.java,204,233,temp,HeartbeatEndpointTask.java,307,330
//,3
public class xxx {
    public HeartbeatEndpointTask build() {
      if (endPointStateMachine == null) {
        LOG.error("No endpoint specified.");
        throw new IllegalArgumentException("A valid endpoint state machine is" +
            " needed to construct HeartbeatEndpointTask task");
      }

      if (conf == null) {
        LOG.error("No config specified.");
        throw new IllegalArgumentException("A valid configration is needed to" +
            " construct HeartbeatEndpointTask task");
      }

      if (datanodeDetails == null) {
        LOG.error("No datanode specified.");
        throw new IllegalArgumentException("A vaild Node ID is needed to " +
            "construct HeartbeatEndpointTask task");
      }

      HeartbeatEndpointTask task = new HeartbeatEndpointTask(this
          .endPointStateMachine, this.conf, this.context);
      task.setDatanodeDetailsProto(datanodeDetails.getProtoBufMessage());
      return task;
    }

};