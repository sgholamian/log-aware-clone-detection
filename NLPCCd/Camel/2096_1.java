//,temp,sample_7427.java,2,13,temp,sample_1839.java,2,13
//,2
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