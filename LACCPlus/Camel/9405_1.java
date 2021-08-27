//,temp,SpringQuartzPersistentStoreRestartAppChangeOptionsTest.java,110,145,temp,SpringQuartzPersistentStoreRestartAppChangeOptionsTest.java,70,108
//,3
public class xxx {
    @Test
    public void testRestartAppChangeTriggerOptions() throws Exception {

        // Test creates application context twice with different simple trigger options in configuration xml.
        // Both times it retrieves back the option, accessing it via trigger (so, using value stored in DB).
        // After that it asserts that two options are not equal.

        // load spring app
        AbstractXmlApplicationContext app = newAppContext("SpringQuartzPersistentStoreRestartAppChangeOptionsTest1.xml");
        app.start();
        CamelContext camel = app.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);
        assertNotNull(camel);
        SimpleTrigger trigger = (SimpleTrigger) getTrigger(camel, "quartzRoute");
        long repeatInterval = trigger.getRepeatInterval();
        app.stop();

        log.info("Restarting ...");
        log.info("Restarting ...");
        log.info("Restarting ...");

        // load spring app
        AbstractXmlApplicationContext app2 = newAppContext("SpringQuartzPersistentStoreRestartAppChangeOptionsTest2.xml");
        app2.start();
        CamelContext camel2 = app2.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);
        assertNotNull(camel2);
        SimpleTrigger trigger2 = (SimpleTrigger) getTrigger(camel2, "quartzRoute");
        long repeatInterval2 = trigger2.getRepeatInterval();
        app2.stop();

        // we're done so let's properly close the application contexts, but close
        // the second app before the first one so that the quartz scheduler running
        // inside it can be properly shutdown
        IOHelper.close(app2, app);

        assertNotEquals(repeatInterval, repeatInterval2);
    }

};