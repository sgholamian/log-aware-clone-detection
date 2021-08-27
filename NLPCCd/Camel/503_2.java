//,temp,sample_4751.java,2,10,temp,sample_4752.java,2,12
//,3
public class xxx {
public Producer createProducer() throws Exception {
if (endpointType == EndpointType.rdd) {
return new RddSparkProducer(this);
} else if (endpointType == EndpointType.dataframe) {
return new DataFrameSparkProducer(this);
} else {


log.info("about to create hive producer");
}
}

};