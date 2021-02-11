//,temp,sample_2740.java,2,14,temp,sample_2739.java,2,12
//,3
public class xxx {
public boolean delete(DataObject obj) {
AsyncCallFuture<CommandResult> future = new AsyncCallFuture<CommandResult>();
this.driver.deleteAsync(obj.getDataStore(), obj, future);
try {
future.get();
} catch (InterruptedException e) {
return false;
} catch (ExecutionException e) {


log.info("failed delete obj");
}
}

};