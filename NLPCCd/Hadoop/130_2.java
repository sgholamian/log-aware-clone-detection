//,temp,sample_914.java,2,14,temp,sample_7041.java,2,13
//,3
public class xxx {
public void stop() throws IOException {
rollEditsRpcExecutor.shutdown();
tailerThread.setShouldRun(false);
tailerThread.interrupt();
try {
tailerThread.join();
} catch (InterruptedException e) {


log.info("edit log tailer thread exited with an exception");
}
}

};