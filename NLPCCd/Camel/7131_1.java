//,temp,sample_7465.java,2,12,temp,sample_3776.java,2,7
//,3
public class xxx {
public boolean rollback(Consumer consumer, Endpoint endpoint, int retryCounter, Exception cause) throws Exception {
Integer times = state.get(consumer);
if (times == null) {
times = 1;
} else {
times += 1;
}


log.info("rollback occurred after times when consuming");
}

};