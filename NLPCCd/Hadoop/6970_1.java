//,temp,sample_3763.java,2,14,temp,sample_151.java,2,11
//,3
public class xxx {
protected void serviceStop() throws Exception {
if (entityTable != null) {
entityTable.close();
}
if (appToFlowTable != null) {
appToFlowTable.close();
}
if (applicationTable != null) {


log.info("closing the application table");
}
}

};