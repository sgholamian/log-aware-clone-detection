//,temp,sample_4212.java,2,17,temp,sample_4210.java,2,17
//,2
public class xxx {
public void dummy_method(){
TemplateApiResult res = new TemplateApiResult(destTemplate);
try {
if (result.isFailed()) {
res.setResult(result.getResult());
destTemplate.processEvent(Event.OperationFailed);
} else {
destTemplate.processEvent(Event.OperationSuccessed, result.getAnswer());
}
future.complete(res);
} catch (Exception e) {


log.info("failed to process copy template callback");
}
}

};