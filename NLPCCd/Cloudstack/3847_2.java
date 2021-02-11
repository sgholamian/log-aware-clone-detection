//,temp,sample_7027.java,2,13,temp,sample_2531.java,2,13
//,2
public class xxx {
public void execute() {
CallContext.current().setEventDetails("AutoScale VM Profile Id: " + getId());
boolean result = _autoScaleService.deleteAutoScaleVmProfile(id);
if (result) {
SuccessResponse response = new SuccessResponse(getCommandName());
setResponseObject(response);
} else {


log.info("failed to delete autoscale vm profile");
}
}

};