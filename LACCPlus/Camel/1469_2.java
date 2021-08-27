//,temp,FileConsumePollEnrichFileTest.java,27,46,temp,PollEnrichFileCustomAggregationStrategyTest.java,28,50
//,3
public class xxx {
    @Test
    public void testPollEnrichCustomAggregationStrategyBody() throws Exception {

        getMockEndpoint("mock:start").expectedBodiesReceived("Start");

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived("Big file");
        mock.expectedFileExists(testFile("enrich/.done/AAA.fin"));
        mock.expectedFileExists(testFile("enrichdata/.done/AAA.dat"));

        template.sendBodyAndHeader(fileUri("enrich"), "Start",
                Exchange.FILE_NAME, "AAA.fin");

        log.info("Sleeping for 0.5 sec before writing enrichdata file");
        Thread.sleep(500);
        template.sendBodyAndHeader(fileUri("enrichdata"), "Big file",
                Exchange.FILE_NAME, "AAA.dat");
        log.info("... write done");

        assertMockEndpointsSatisfied();

        assertFileNotExists(testFile("enrichdata/AAA.dat.camelLock"));
    }

};