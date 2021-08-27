//,temp,sample_423.java,2,12,temp,sample_424.java,2,12
//,2
public class xxx {
public void afterTestMethod(TestContext testContext) throws Exception {
StopWatch watch = threadStopWatch.get();
if (watch != null) {
long time = watch.stop();
Logger log = LoggerFactory.getLogger(testContext.getTestClass());
log.info("********************************************************************************");


log.info("took millis");
}
}

};