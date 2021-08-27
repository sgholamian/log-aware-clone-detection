//,temp,SpringQuartzPersistentStoreRestartAppChangeOptionsTest.java,147,178,temp,SpringQuartzPersistentStoreRestartAppTest.java,34,82
//,3
public class xxx {
    @Test
    public void testQuartzPersistentStoreRestart() throws Exception {
        // load spring app
        AbstractXmlApplicationContext app
                = newAppContext("SpringQuartzPersistentStoreTest.xml");

        app.start();

        CamelContext camel = app.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);
        assertNotNull(camel);

        MockEndpoint mock = camel.getEndpoint("mock:result", MockEndpoint.class);
        mock.expectedMinimumMessageCount(2);

        mock.assertIsSatisfied();

        app.stop();

        log.info("Restarting ...");
        log.info("Restarting ...");
        log.info("Restarting ...");

        // NOTE:
        // To test a restart where the app has crashed, then you can in QuartzEndpoint
        // in the doShutdown method, then remove the following code line
        //  deleteTrigger(getTrigger());
        // then when we restart then there is old stale data which QuartzComponent
        // is supposed to handle and start again

        // load spring app
        AbstractXmlApplicationContext app2 = newAppContext("SpringQuartzPersistentStoreRestartTest.xml");

        app2.start();

        CamelContext camel2 = app2.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);
        assertNotNull(camel2);

        MockEndpoint mock2 = camel2.getEndpoint("mock:result", MockEndpoint.class);
        mock2.expectedMinimumMessageCount(2);

        mock2.assertIsSatisfied();

        app2.stop();

        // we're done so let's properly close the application contexts, but close
        // the second app before the first one so that the quartz scheduler running
        // inside it can be properly shutdown
        IOHelper.close(app2, app);
    }

};