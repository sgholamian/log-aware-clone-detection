//,temp,LuceneIndexAndQueryProducerIT.java,189,237,temp,LuceneIndexAndQueryProducerIT.java,154,187
//,3
public class xxx {
    @Test
    public void testLuceneWildcardQueryProducer() throws Exception {
        MockEndpoint mockSearchEndpoint = getMockEndpoint("mock:searchResult");

        context.addRoutes(new RouteBuilder() {
            public void configure() {

                from("direct:start").setHeader("QUERY", constant("Grouc?? Marx"))
                        .to("lucene:searchIndex:query?analyzer=#stdAnalyzer&indexDir=#std&maxHits=20").to("direct:next");

                from("direct:next").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Hits hits = exchange.getIn().getBody(Hits.class);
                        printResults(hits);
                    }

                    private void printResults(Hits hits) {
                        LOG.debug("Number of hits: " + hits.getNumberOfHits());
                        for (int i = 0; i < hits.getNumberOfHits(); i++) {
                            LOG.debug("Hit " + i + " Index Location:" + hits.getHit().get(i).getHitLocation());
                            LOG.debug("Hit " + i + " Score:" + hits.getHit().get(i).getScore());
                            LOG.debug("Hit " + i + " Data:" + hits.getHit().get(i).getData());
                        }
                    }
                }).to("mock:searchResult");
            }
        });
        context.start();
        LOG.debug("------------Beginning  LuceneQueryProducer Wildcard Test---------------");

        sendQuery();
        mockSearchEndpoint.assertIsSatisfied();
        LOG.debug("------------Completed LuceneQueryProducer Wildcard Test---------------");
    }

};