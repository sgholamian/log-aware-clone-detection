//,temp,LuceneQueryProcessorIT.java,94,136,temp,LuceneQueryProcessorIT.java,59,83
//,3
public class xxx {
            public void configure() {

                try {
                    from("direct:start").setHeader("QUERY", constant("Rodney Dangerfield"))
                            .process(new LuceneQueryProcessor("target/stdindexDir", analyzer, null, 20, 20)).to("direct:next");
                } catch (Exception e) {
                    LOG.warn("Unhandled exception: {}", e.getMessage(), e);
                }

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

};