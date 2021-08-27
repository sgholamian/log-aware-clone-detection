//,temp,sample_1841.java,2,12,temp,sample_1834.java,2,12
//,3
public class xxx {
public void testRestartAppChangeCronExpression() throws Exception {
app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz2/SpringQuartzPersistentStoreRestartAppChangeCronExpressionTest1.xml");
app.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
assertNotNull(camel);
String cronExpression = ((CronTrigger) getTrigger(camel, "quartzRoute")).getCronExpression();
app.stop();


log.info("restarting");
}

};