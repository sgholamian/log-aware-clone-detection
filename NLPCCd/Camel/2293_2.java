//,temp,sample_1169.java,2,11,temp,sample_2116.java,2,11
//,2
public class xxx {
protected void doStart() throws Exception {
if (writeConcern != null && writeConcernRef != null) {
String msg = "Cannot set both writeConcern and writeConcernRef at the same time. Respective values: " + writeConcern + ", " + writeConcernRef + ". Aborting initialization.";
throw new IllegalArgumentException(msg);
}
mongoConnection = CamelContextHelper.mandatoryLookup(getCamelContext(), connectionBean, MongoClient.class);


log.info("resolved the connection with the name as");
}

};