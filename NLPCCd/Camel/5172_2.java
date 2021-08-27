//,temp,sample_5666.java,2,10,temp,sample_2446.java,2,10
//,3
public class xxx {
protected void doStop() throws Exception {
Collection<AwaitThread> threads = browse();
int count = threads.size();
if (count > 0) {


log.info("shutting down while there are still inflight threads currently blocked");
}
}

};