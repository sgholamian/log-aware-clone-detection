//,temp,sample_1840.java,2,12,temp,sample_5454.java,2,14
//,3
public class xxx {
public void testRestartAppChangeTriggerType() throws Exception {
app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz2/SpringQuartzPersistentStoreRestartAppChangeCronExpressionTest1.xml");
app.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
assertNotNull(camel);
assertTrue(getTrigger(camel, "quartzRoute") instanceof CronTrigger);
app.stop();


log.info("restarting");
}

};