//,temp,sample_5761.java,2,12,temp,sample_1321.java,2,12
//,2
public class xxx {
public void testPollEnrichDefaultAggregationStrategyBody() throws Exception {
getMockEndpoint("mock:start").expectedBodiesReceived("Start");
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("Big file");
mock.expectedFileExists("target/enrich/.done/AAA.fin");
mock.expectedFileExists("target/enrichdata/.done/AAA.dat");
Thread.sleep(250);


log.info("file log info done");
}

};