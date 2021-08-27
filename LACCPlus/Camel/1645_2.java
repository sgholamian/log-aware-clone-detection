//,temp,LuceneIndexAndQueryProducerIT.java,205,215,temp,LuceneIndexAndQueryProducerIT.java,135,142
//,3
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