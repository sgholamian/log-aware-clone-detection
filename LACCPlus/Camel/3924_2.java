//,temp,JmsToJmsTransactedSecurityTest.java,37,62,temp,LuceneIndexAndQueryProducerIT.java,94,116
//,3
public class xxx {
    @Test
    public void testLuceneIndexProducer() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:result");

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:start").to("lucene:stdQuotesIndex:insert?analyzer=#stdAnalyzer&indexDir=#std&srcDir=#load_dir")
                        .to("lucene:simpleQuotesIndex:insert?analyzer=#simpleAnalyzer&indexDir=#simple&srcDir=#load_dir")
                        .to("lucene:whitespaceQuotesIndex:insert?analyzer=#whitespaceAnalyzer&indexDir=#whitespace&srcDir=#load_dir")
                        .to("mock:result");

            }
        });
        context.start();

        LOG.debug("------------Beginning LuceneIndexProducer Test---------------");
        for (String quote : humorousQuotes) {
            sendRequest(quote);
        }

        mockEndpoint.assertIsSatisfied();
        LOG.debug("------------Completed LuceneIndexProducer Test---------------");
    }

};