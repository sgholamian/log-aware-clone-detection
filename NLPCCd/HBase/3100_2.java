//,temp,sample_3836.java,2,7,temp,sample_5182.java,2,10
//,3
public class xxx {
private byte[] waitOnFuture(final Future<byte[]> future) throws Exception {
try {
return future.get(5, TimeUnit.SECONDS);
} catch (ExecutionException e) {


log.info("ExecutionException");
}
}

};