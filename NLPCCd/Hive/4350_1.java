//,temp,sample_2686.java,2,17,temp,sample_3212.java,2,17
//,3
public class xxx {
public void dummy_method(){
executor = Runtime.getRuntime().exec(cmdLine, env, new File(workDir));
CachingPrintStream errPrintStream = new CachingPrintStream(SessionState.getConsole().getChildErrStream());
StreamPrinter outPrinter = new StreamPrinter( executor.getInputStream(), null, SessionState.getConsole().getChildOutStream());
StreamPrinter errPrinter = new StreamPrinter( executor.getErrorStream(), null, errPrintStream);
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