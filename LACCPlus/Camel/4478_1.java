//,temp,SpringQuartzTwoAppsClusteredFailoverTest.java,37,95,temp,SpringQuartzConsumerTwoAppsClusteredFailoverTest.java,37,87
//,3
public class xxx {
    @Test
    public void testQuartzPersistentStoreClusteredApp() throws Exception {
        // boot up the database the two apps are going to share inside a clustered quartz setup
        AbstractXmlApplicationContext db = newAppContext("SpringQuartzClusteredAppDatabase.xml");

        // now launch the first clustered app which will acquire the quartz database lock and become the master
        AbstractXmlApplicationContext app = newAppContext("SpringQuartzClusteredAppOne.xml");

        // as well as the second one which will run in slave mode as it will not be able to acquire the same lock
        AbstractXmlApplicationContext app2 = newAppContext("SpringQuartzClusteredAppTwo.xml");

        CamelContext camel = app.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);

        MockEndpoint mock = camel.getEndpoint("mock:result", MockEndpoint.class);
        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived("clustering PINGS!");

        // wait a bit to make sure the route has already been properly started through the given route policy
        Thread.sleep(500);

        app.getBean("template", ProducerTemplate.class).sendBody("direct:start", "clustering");

        mock.assertIsSatisfied();

        // now let's simulate a crash of the first app (the quartz instance 'app-one')
        log.warn("The first app is going to crash NOW!");
        // we need to stop the Scheduler first as the CamelContext will gracefully shutdown and
        // delete all scheduled jobs, so there would be nothing for the second CamelContext to
        // failover from
        app.getBean(Scheduler.class).shutdown();
        IOHelper.close(app);

        log.warn("Crashed...");
        log.warn("Crashed...");
        log.warn("Crashed...");

        // wait long enough until the second app takes it over...
        Thread.sleep(2000);
        // inside the logs one can then clearly see how the route of the second app ('app-two') gets started:
        // 2013-09-29 08:15:17,038 [main           ] WARN  tzTwoAppsClusteredFailoverTest:65   - Crashed...
        // 2013-09-29 08:15:17,038 [main           ] WARN  tzTwoAppsClusteredFailoverTest:66   - Crashed...
        // 2013-09-29 08:15:17,038 [main           ] WARN  tzTwoAppsClusteredFailoverTest:67   - Crashed...
        // 2013-09-29 08:15:32,001 [_ClusterManager] INFO  LocalDataSourceJobStore       :3567 - ClusterManager: detected 1 failed or restarted instances.
        // 2013-09-29 08:15:32,001 [_ClusterManager] INFO  LocalDataSourceJobStore       :3426 - ClusterManager: Scanning for instance "app-one"'s failed in-progress jobs.
        // 2013-09-29 08:15:32,024 [eduler_Worker-1] INFO  SpringCamelContext            :2183 - Route: myRoute started and consuming from: Endpoint[direct://start]

        CamelContext camel2 = app2.getBean("camelContext2-" + getClass().getSimpleName(), CamelContext.class);

        MockEndpoint mock2 = camel2.getEndpoint("mock:result", MockEndpoint.class);
        mock2.expectedMessageCount(1);
        mock2.expectedBodiesReceived("clustering PONGS!");

        app2.getBean("template", ProducerTemplate.class).sendBody("direct:start", "clustering");

        mock2.assertIsSatisfied();

        // and as the last step shutdown the second app as well as the database
        IOHelper.close(app2, db);
    }

};