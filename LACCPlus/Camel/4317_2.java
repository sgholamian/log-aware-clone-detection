//,temp,OnCompletionProcessor.java,322,359,temp,OnCompletionProcessor.java,279,320
//,3
public class xxx {
        @Override
        @SuppressWarnings("unchecked")
        public void onComplete(final Exchange exchange) {
            String currentRouteId = ExchangeHelper.getRouteId(exchange);
            if (!routeScoped && currentRouteId != null && !routeId.equals(currentRouteId)) {
                return;
            }

            if (routeScoped) {
                // check if we visited the route
                List<String> routeIds = exchange.getProperty(ExchangePropertyKey.ON_COMPLETION_ROUTE_IDS, List.class);
                if (routeIds == null || !routeIds.contains(routeId)) {
                    return;
                }
            }

            if (onFailureOnly) {
                return;
            }

            if (onWhen != null && !onWhen.matches(exchange)) {
                // predicate did not match so do not route the onComplete
                return;
            }

            // must use a copy as we dont want it to cause side effects of the original exchange
            final Exchange copy = prepareExchange(exchange);

            if (executorService != null) {
                executorService.submit(new Callable<Exchange>() {
                    public Exchange call() throws Exception {
                        LOG.debug("Processing onComplete: {}", copy);
                        doProcess(processor, copy);
                        return copy;
                    }
                });
            } else {
                // run without thread-pool
                LOG.debug("Processing onComplete: {}", copy);
                doProcess(processor, copy);
            }
        }

};