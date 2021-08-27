//,temp,sample_7425.java,2,12,temp,sample_7428.java,2,13
//,3
public class xxx {
public void testRestartAppChangeCronExpression() throws Exception {
app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz/SpringQuartzPersistentStoreRestartAppChangeCronExpressionTest1.xml");
app.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
assertNotNull(camel);
String cronExpression = ((CronTrigger) getTrigger(camel, "quartzRoute")).getCronExpression();
app.stop();


log.info("restarting");
}

};