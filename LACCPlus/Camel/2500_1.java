//,temp,LuceneIndexAndQueryProducerIT.java,193,225,temp,LuceneQueryProcessorIT.java,101,126
//,3
public class xxx {
            public void configure() {

                from("direct:start").setHeader("QUERY", constant("Grouc?? Marx"))
                        .setHeader("RETURN_LUCENE_DOCS", constant("true"))
                        .to("lucene:searchIndex:query?analyzer=#stdAnalyzer&indexDir=#std&maxHits=20").to("direct:next");

                from("direct:next").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Hits hits = exchange.getIn().getBody(Hits.class);
                        printResults(hits);
                    }

                    private void printResults(Hits hits) throws Exception {
                        LOG.debug("Number of hits: " + hits.getNumberOfHits());
                        for (int i = 0; i < hits.getNumberOfHits(); i++) {
                            LOG.debug("Hit " + i + " Index Location:" + hits.getHit().get(i).getHitLocation());
                            LOG.debug("Hit " + i + " Score:" + hits.getHit().get(i).getScore());
                            LOG.debug("Hit " + i + " Data:" + hits.getHit().get(i).getData());
                            if (hits.getHit().get(i).getDocument() == null) {
                                throw new Exception("Failed to return lucene documents");
                            }
                        }
                    }
                }).to("mock:searchResult").process(exchange -> {
                    Hits hits = exchange.getIn().getBody(Hits.class);
                    if (hits == null) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("NO_LUCENE_DOCS_ERROR", "NO LUCENE DOCS FOUND");
                        exchange.getContext().setGlobalOptions(map);
                    }
                    LOG.debug("Number of hits: " + hits.getNumberOfHits());
                });
            }

};