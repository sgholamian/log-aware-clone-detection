//,temp,sample_2686.java,2,17,temp,sample_3212.java,2,17
//,3
public class xxx {
public void dummy_method(){
LogRedirector.redirect( Thread.currentThread().getName() + "-LocalTask-" + getName() + "-stderr", new LogRedirector(executor.getErrorStream(), LOG, callback));
CachingPrintStream errPrintStream = new CachingPrintStream(System.err);
StreamPrinter outPrinter = new StreamPrinter(executor.getInputStream(), null, System.out);
StreamPrinter errPrinter = new StreamPrinter(executor.getErrorStream(), null, errPrintStream);
outPrinter.start();
errPrinter.start();
int exitVal = jobExecHelper.progressLocal(executor, getId());
outPrinter.join();
errPrinter.join();
if (exitVal != 0) {


log.info("execution failed with exit status");
}
}

};