//,temp,sample_2579.java,2,17,temp,sample_3210.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (s_logger.isDebugEnabled()) {
}
final Command[] cmds = new Command[1];
cmds[0] = new PropagateResourceEventCommand(agentId, event);
final String AnsStr = _clusterMgr.execute(msPeer, agentId, _gson.toJson(cmds), true);
if (AnsStr == null) {
throw new AgentUnavailableException(agentId);
}
final Answer[] answers = _gson.fromJson(AnsStr, Answer[].class);
if (s_logger.isDebugEnabled()) {


log.info("result for agent change is");
}
}

};