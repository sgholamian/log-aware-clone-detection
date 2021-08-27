//,temp,AggregateCompletionOnNewCorrelationGroupWithCanPreCompleteTest.java,121,143,temp,AggregateCompletionOnNewCorrelationGroupWithCanPreCompleteTest.java,88,119
//,3
public class xxx {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            LOG.debug("aggregate");

            if (oldExchange == null) {
                LOG.debug("aggregate oldExchange[{}] newExchangeId[{}]",
                        oldExchange,
                        newExchange.getExchangeId());
                return newExchange;
            }

            String body1 = oldExchange.getIn().getBody(String.class);
            String body2 = newExchange.getIn().getBody(String.class);
            LOG.debug("aggregate body1[{}] body2[{}] [{}] [{}]", body1, body2,
                    oldExchange.getExchangeId(), newExchange.getExchangeId());

            oldExchange.getIn().setBody(body1 + body2);

            LOG.debug("aggregate [{}] [{}] [{}]", oldExchange.getIn().getBody(),
                    oldExchange.getExchangeId(), newExchange.getExchangeId());

            return oldExchange;
        }

};