//,temp,sample_1688.java,2,11,temp,sample_9274.java,2,11
//,2
public class xxx {
public void execute() {
boolean result = false;
try {
result = _autoScaleService.deleteCondition(getId());
} catch (ResourceInUseException ex) {


log.info("exception");
}
}

};