//,temp,sample_4870.java,2,10,temp,sample_4869.java,2,8
//,3
public class xxx {
protected ChangeModel createChangeModel() {
if (ServerInstance.this.options.getBufferingPeriod() != null && ServerInstance.this.options.getBufferingPeriod() > 0) {
return makeBufferingChangeModel(ServerInstance.this.options.getBufferingPeriod());
} else {


log.info("creating instant change model");
}
}

};