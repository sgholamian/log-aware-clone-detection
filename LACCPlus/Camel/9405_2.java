//,temp,SpringQuartzPersistentStoreRestartAppChangeOptionsTest.java,110,145,temp,SpringQuartzPersistentStoreRestartAppChangeOptionsTest.java,70,108
//,3
public class xxx {
    @Test
    public void testRestartAppChangeCronExpression() throws Exception {

        // Test creates application context twice with different cron expressions in configuration xml.
        // Both times it retrieves back the cron expression, accessing it via trigger (so, using value stored in DB).
        // After that it asserts that two cron expressions are not equal.

        // load spring app
        app = newAppContext("SpringQuartzPersistentStoreRestartAppChangeCronExpressionTest1.xml");
        app.start();
        CamelContext camel = app.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);
        assertNotNull(camel);
        String cronExpression = ((CronTrigger) getTrigger(camel, "quartzRoute")).getCronExpression();
        app.stop();

        log.info("Restarting ...");
        log.info("Restarting ...");
        log.info("Restarting ...");

        // load spring app
        app2 = newAppContext("SpringQuartzPersistentStoreRestartAppChangeCronExpressionTest2.xml");
        app2.start();
        CamelContext camel2 = app2.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);
        assertNotNull(camel2);
        String cronExpression2 = ((CronTrigger) getTrigger(camel2, "quartzRoute")).getCronExpression();
        app2.stop();

        assertNotEquals(cronExpression, cronExpression2);

        // load spring app
        app3 = newAppContext("SpringQuartzPersistentStoreRestartAppChangeCronExpressionTest3.xml");
        app3.start();
        CamelContext camel3 = app3.getBean("camelContext3", CamelContext.class);
        assertNotNull(camel3);
        String cronExpression3 = ((CronTrigger) getTrigger(camel3, "quartzRoute")).getCronExpression();
        app3.stop();

        assertEquals(cronExpression2, cronExpression3);
    }

};