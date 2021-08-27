//,temp,sample_5732.java,2,10,temp,sample_2737.java,2,12
//,3
public class xxx {
private List<Runnable> doShutdownNow(ExecutorService executorService, boolean failSafe) {
ObjectHelper.notNull(executorService, "executorService");
List<Runnable> answer = null;
if (!executorService.isShutdown()) {
if (failSafe) {


log.info("forcing shutdown of executorservice");
}
}
}

};