//,temp,sample_1904.java,2,19,temp,sample_1910.java,2,19
//,2
public class xxx {
public void dummy_method(){
UploadStatusCommand cmd = new UploadStatusCommand(template.getUuid(), EntityType.Template);
if (host != null && host.getManagementServerId() != null) {
if (_nodeId == host.getManagementServerId().longValue()) {
Answer answer = null;
try {
answer = ep.sendMessage(cmd);
} catch (CloudRuntimeException e) {
answer = new UploadStatusAnswer(cmd, UploadStatus.UNKNOWN, e.getMessage());
}
if (answer == null || !(answer instanceof UploadStatusAnswer)) {


log.info("no or invalid answer corresponding to uploadstatuscommand for template");
}
}
}
}

};