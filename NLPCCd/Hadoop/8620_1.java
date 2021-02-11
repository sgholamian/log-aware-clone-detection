//,temp,sample_3762.java,2,11,temp,sample_151.java,2,11
//,3
public class xxx {
protected void serviceStop() throws Exception {
if (entityTable != null) {
entityTable.close();
}
if (appToFlowTable != null) {


log.info("closing the app flow table");
}
}

};