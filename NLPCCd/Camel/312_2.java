//,temp,sample_2636.java,2,12,temp,sample_1320.java,2,11
//,3
public class xxx {
public void testPollEnrichDefaultAggregationStrategyBody() throws Exception {
getMockEndpoint("mock:start").expectedBodiesReceived("Start");
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("Big file");
mock.expectedFileExists("target/enrich/.done/AAA.fin");
mock.expectedFileExists("target/enrichdata/.done/AAA.dat");


log.info("file log info file");
}

};