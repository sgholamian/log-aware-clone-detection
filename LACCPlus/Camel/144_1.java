//,temp,LuceneIndexAndQueryProducerIT.java,170,177,temp,LuceneQueryProcessorIT.java,74,81
//,2
public class xxx {
                    private void printResults(Hits hits) {
                        LOG.debug("Number of hits: " + hits.getNumberOfHits());
                        for (int i = 0; i < hits.getNumberOfHits(); i++) {
                            LOG.debug("Hit " + i + " Index Location:" + hits.getHit().get(i).getHitLocation());
                            LOG.debug("Hit " + i + " Score:" + hits.getHit().get(i).getScore());
                            LOG.debug("Hit " + i + " Data:" + hits.getHit().get(i).getData());
                        }
                    }

};