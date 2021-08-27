//,temp,sample_2687.java,2,17,temp,sample_3213.java,2,17
//,2
public class xxx {
public void dummy_method(){
outPrinter.start();
errPrinter.start();
int exitVal = jobExecHelper.progressLocal(executor, getId());
outPrinter.join();
errPrinter.join();
if (exitVal != 0) {
if (SessionState.get() != null) {
SessionState.get().addLocalMapRedErrors(getId(), errPrintStream.getOutput());
}
} else {


log.info("execution completed successfully");
}
}

};