//,temp,sample_5343.java,2,18,temp,sample_5350.java,2,18
//,2
public class xxx {
public void dummy_method(){
for (Future<Boolean> future : taskList) {
try {
if (!future.get(5, TimeUnit.SECONDS)) {
throw new Exception("Could not move region Exception");
}
} catch (InterruptedException e) {
throw e;
} catch (ExecutionException e) {
throw e;
} catch (CancellationException e) {


log.info("thread for moving region cancelled timeout for cancellation secs");
}
}
}

};