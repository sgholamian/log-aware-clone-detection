//,temp,AggregateCompletionOnNewCorrelationGroupWithCanPreCompleteTest.java,121,143,temp,AggregateCompletionOnNewCorrelationGroupWithCanPreCompleteTest.java,88,119
//,3
public class xxx {
        @Override
        public boolean preComplete(Exchange oldExchange, Exchange newExchange) {
            boolean preComplete = false;

            String body1;
            String body2;
            String oldExchangeId;
            String newExchangeId;

            if (oldExchange == null) {
                oldExchangeId = null;
                newExchangeId = newExchange.getExchangeId();
                body1 = null;
                body2 = newExchange.getIn().getBody(String.class);
            } else {
                body1 = oldExchange.getIn().getBody(String.class);
                body2 = newExchange.getIn().getBody(String.class);

                oldExchangeId = oldExchange.getExchangeId();
                newExchangeId = newExchange.getExchangeId();
            }

            LOG.debug("preComplete body1[{}] body2[{}] [{}] [{}]", body1, body2,
                    oldExchangeId, newExchangeId);

            if (newExchange.getIn().getBody().equals("end")) {
                preComplete = true;
            }

            LOG.debug("preComplete[{}]", preComplete);
            return preComplete;
        }

};