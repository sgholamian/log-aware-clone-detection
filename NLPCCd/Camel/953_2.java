//,temp,sample_7425.java,2,12,temp,sample_7428.java,2,13
//,3
public class xxx {
public void testRestartAppChangeTriggerOptions() throws Exception {
AbstractXmlApplicationContext app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz/SpringQuartzPersistentStoreRestartAppChangeOptionsTest1.xml");
app.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
assertNotNull(camel);
SimpleTrigger trigger = (SimpleTrigger) getTrigger(camel, "quartzRoute");
long repeatInterval = trigger.getRepeatInterval();
app.stop();


log.info("restarting");
}

};