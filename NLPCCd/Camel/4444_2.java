//,temp,sample_7431.java,2,12,temp,sample_7430.java,2,12
//,2
public class xxx {
public void testRestartAppChangeTriggerType() throws Exception {
app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz/SpringQuartzPersistentStoreRestartAppChangeCronExpressionTest1.xml");
app.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
assertNotNull(camel);
assertTrue(getTrigger(camel, "quartzRoute") instanceof CronTrigger);
app.stop();


log.info("restarting");
}

};