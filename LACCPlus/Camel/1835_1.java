//,temp,SpringQuartzPersistentStoreRestartAppChangeOptionsTest.java,147,178,temp,SpringQuartzPersistentStoreRestartAppTest.java,34,82
//,3
public class xxx {
    @Test
    public void testRestartAppChangeTriggerType() throws Exception {

        // Test creates application context twice with different simple trigger options in configuration xml.
        // Both times it retrieves back the option, accessing it via trigger (so, using value stored in DB).
        // After that it asserts that two options are not equal.

        // load spring app
        app = newAppContext("SpringQuartzPersistentStoreRestartAppChangeCronExpressionTest1.xml");
        app.start();
        CamelContext camel = app.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);
        assertNotNull(camel);
        assertTrue(getTrigger(camel, "quartzRoute") instanceof CronTrigger);
        app.stop();

        log.info("Restarting ...");
        log.info("Restarting ...");
        log.info("Restarting ...");

        // load spring app
        AbstractXmlApplicationContext app2 = newAppContext("SpringQuartzPersistentStoreRestartAppChangeOptionsTest2.xml");
        app2.start();
        CamelContext camel2 = app2.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);
        assertNotNull(camel2);
        assertTrue(getTrigger(camel2, "quartzRoute") instanceof SimpleTrigger);
        app2.stop();

        // we're done so let's properly close the application contexts, but close
        // the second app before the first one so that the quartz scheduler running
        // inside it can be properly shutdown
        IOHelper.close(app2, app);
    }

};