//,temp,sample_5199.java,2,16,temp,sample_5200.java,2,16
//,2
public class xxx {
public void dummy_method(){
URI agentUri = null;
try {
final String cmdName = ModifyVmNicConfigCommand.class.getName();
agentUri = new URI("https", null, _agentIp, _port, "/api/HypervResource/" + cmdName, null, null);
} catch (final URISyntaxException e) {
final String errMsg = "Could not generate URI for Hyper-V agent";
s_logger.error(errMsg, e);
}
final String ansStr = postHttpRequest(s_gson.toJson(modifynic), agentUri);
final Answer[] result = s_gson.fromJson(ansStr, Answer[].class);


log.info("executerequest received response");
}

};