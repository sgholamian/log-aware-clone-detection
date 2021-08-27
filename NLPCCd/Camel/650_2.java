//,temp,sample_2739.java,2,16,temp,sample_2738.java,2,13
//,3
public class xxx {
private List<Runnable> doShutdownNow(ExecutorService executorService, boolean failSafe) {
ObjectHelper.notNull(executorService, "executorService");
List<Runnable> answer = null;
if (!executorService.isShutdown()) {
if (failSafe) {
} else {


log.info("forcing shutdown of executorservice");
}
}
}

};