//,temp,sample_2707.java,2,10,temp,sample_2702.java,2,10
//,2
public class xxx {
public void testHiveEventCounterAppender() throws Exception {
Logger logger = LogManager.getRootLogger();
HiveEventCounter appender = HiveEventCounter.createInstance("EventCounter", true, null, null);
appender.addToLogger(logger.getName(), Level.INFO);
appender.start();


log.info("Test");
}

};