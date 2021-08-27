//,temp,sample_28.java,2,11,temp,sample_1524.java,2,11
//,2
public class xxx {
public void stop() throws Exception {
int consumers = channelFactory.consumers();
if (consumers == 0) {
super.stop();
} else {


log.info("bootstrapfactory on port has registered consumers so cannot stop yet");
}
}

};