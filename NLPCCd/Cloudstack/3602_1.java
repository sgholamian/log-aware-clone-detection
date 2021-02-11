//,temp,sample_7027.java,2,13,temp,sample_3894.java,2,13
//,2
public class xxx {
public void execute() {
CallContext.current().setEventDetails("AutoScale Policy Id: " + getId());
boolean result = _autoScaleService.deleteAutoScalePolicy(id);
if (result) {
SuccessResponse response = new SuccessResponse(getCommandName());
setResponseObject(response);
} else {


log.info("failed to delete autoscale policy");
}
}

};