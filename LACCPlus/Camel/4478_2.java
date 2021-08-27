//,temp,SpringQuartzTwoAppsClusteredFailoverTest.java,37,95,temp,SpringQuartzConsumerTwoAppsClusteredFailoverTest.java,37,87
//,3
public class xxx {
    @Test
    public void testQuartzPersistentStoreClusteredApp() throws Exception {
        // boot up the database the two apps are going to share inside a clustered quartz setup
        AbstractXmlApplicationContext db = newAppContext("SpringQuartzConsumerClusteredAppDatabase.xml");
        db.start();

        // now launch the first clustered app which will acquire the quartz database lock and become the master
        AbstractXmlApplicationContext app = newAppContext("SpringQuartzConsumerClusteredAppOne.xml");
        app.start();

        // as well as the second one which will run in slave mode as it will not be able to acquire the same lock
        AbstractXmlApplicationContext app2 = newAppContext("SpringQuartzConsumerClusteredAppTwo.xml");
        app2.start();

        CamelContext camel = app.getBean("camelContext-" + getClass().getSimpleName(), CamelContext.class);

        MockEndpoint mock = camel.getEndpoint("mock:result", MockEndpoint.class);
        mock.expectedMinimumMessageCount(3);
        mock.expectedMessagesMatches(new ClusteringPredicate(true));

        mock.assertIsSatisfied();

        // now let's simulate a crash of the first app (the quartz instance 'app-one')
        log.warn("The first app is going to crash NOW!");
        IOHelper.close(app);

        log.warn("Crashed...");
        log.warn("Crashed...");
        log.warn("Crashed...");

        // wait long enough until the second app takes it over...
        Thread.sleep(2000);
        // inside the logs one can then clearly see how the route of the second app ('app-two') starts consuming:
        // 2013-09-30 11:22:20,349 [main           ] WARN  erTwoAppsClusteredFailoverTest - Crashed...
        // 2013-09-30 11:22:20,349 [main           ] WARN  erTwoAppsClusteredFailoverTest - Crashed...
        // 2013-09-30 11:22:20,349 [main           ] WARN  erTwoAppsClusteredFailoverTest - Crashed...
        // 2013-09-30 11:22:35,340 [_ClusterManager] INFO  LocalDataSourceJobStore        - ClusterManager: detected 1 failed or restarted instances.
        // 2013-09-30 11:22:35,340 [_ClusterManager] INFO  LocalDataSourceJobStore        - ClusterManager: Scanning for instance "app-one"'s failed in-progress jobs.
        // 2013-09-30 11:22:35,369 [eduler_Worker-1] INFO  triggered                      - Exchange[ExchangePattern: InOnly, BodyType: String, Body: clustering PONGS!]

        CamelContext camel2 = app2.getBean("camelContext2-" + getClass().getSimpleName(), CamelContext.class);

        MockEndpoint mock2 = camel2.getEndpoint("mock:result", MockEndpoint.class);
        mock2.expectedMinimumMessageCount(3);
        mock2.expectedMessagesMatches(new ClusteringPredicate(false));

        mock2.assertIsSatisfied();

        // and as the last step shutdown the second app as well as the database
        IOHelper.close(app2, db);
    }

};