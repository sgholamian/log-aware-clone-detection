//,temp,sample_3068.java,2,11,temp,sample_3069.java,2,12
//,2
public class xxx {
protected void doStart() throws Exception {
super.doStart();
if (client == null) {
if (configuration.isLocal()) {
} else {


log.info("joining elasticsearch cluster");
}
}
}

};