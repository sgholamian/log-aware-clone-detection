//,temp,sample_7837.java,2,15,temp,sample_7838.java,2,16
//,3
public class xxx {
public DeleteHostAnswer deleteHost(HostVO host, boolean isForced, boolean isForceDeleteStorage) throws UnableDeleteHostException {
if (host.getType() != Host.Type.Routing || (host.getHypervisorType() != HypervisorType.KVM && host.getHypervisorType() != HypervisorType.LXC)) {
return null;
}
_resourceMgr.deleteRoutingHost(host, isForced, isForceDeleteStorage);
try {
ShutdownCommand cmd = new ShutdownCommand(ShutdownCommand.DeleteHost, null);
_agentMgr.send(host.getId(), cmd);
} catch (AgentUnavailableException e) {
} catch (OperationTimedoutException e) {


log.info("sending shutdowncommand failed");
}
}

};