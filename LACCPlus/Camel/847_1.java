//,temp,DisruptorProducer.java,90,110,temp,SedaProducer.java,73,92
//,3
public class xxx {
                @Override
                public void onDone(final Exchange response) {
                    // check for timeout, which then already would have invoked the latch
                    if (latch.getCount() == 0) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("{}. Timeout occurred so response will be ignored: {}", this,
                                    response.getMessage());
                        }
                    } else {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("{} with response: {}", this,
                                    response.getMessage());
                        }
                        try {
                            ExchangeHelper.copyResults(exchange, response);
                        } finally {
                            // always ensure latch is triggered
                            latch.countDown();
                        }
                    }
                }

};