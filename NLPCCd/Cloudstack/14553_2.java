//,temp,sample_2967.java,2,10,temp,sample_3175.java,2,10
//,2
public class xxx {
public boolean executeUserRequest(final long hostId, final Event event) throws AgentUnavailableException {
if (event == Event.AgentDisconnected) {
if (s_logger.isDebugEnabled()) {


log.info("received agent disconnect event for host");
}
}
}

};