//,temp,sample_3413.java,2,9,temp,sample_2989.java,2,11
//,3
public class xxx {
public CommandProcessorResponse run(String command) throws CommandNeedRetryException {
SessionState ss = SessionState.get();
try {
ss.loadReloadableAuxJars();
} catch (IOException e) {


log.info("fail to reload auxiliary jar files");
}
}

};