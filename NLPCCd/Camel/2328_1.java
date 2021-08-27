//,temp,sample_5760.java,2,11,temp,sample_2635.java,2,11
//,2
public class xxx {
public void testPollEnrichCustomAggregationStrategyBody() throws Exception {
getMockEndpoint("mock:start").expectedBodiesReceived("Start");
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("Big file");
mock.expectedFileExists("target/enrich/.done/AAA.fin");
mock.expectedFileExists("target/enrichdata/.done/AAA.dat");


log.info("file log info file");
}

};