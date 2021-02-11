//,temp,sample_6767.java,2,17,temp,sample_6773.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
if (resource != null) {
answer = resource.executeRequest(cmds[i]);
if (answer == null) {
answer = new Answer(cmds[i], false, "Resource returned null answer");
}
} else {
answer = new Answer(cmds[i], false, "Agent is disconnected");
}
} catch (Throwable t) {


log.info("throwable caught while executing command");
}
}

};