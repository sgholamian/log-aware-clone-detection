//,temp,sample_3763.java,2,14,temp,sample_1240.java,2,12
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