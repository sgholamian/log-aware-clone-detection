//,temp,sample_2703.java,2,10,temp,sample_2697.java,2,10
//,3
public class xxx {
public void testStringAppender() throws Exception {
Logger logger = LogManager.getRootLogger();
StringAppender appender = StringAppender.createStringAppender("%m");
appender.addToLogger(logger.getName(), Level.INFO);
appender.start();


log.info("world");
}

};