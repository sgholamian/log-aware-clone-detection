//,temp,ClusteredRoutePolicy.java,382,397,temp,DefaultSupervisingRouteController.java,853,871
//,3
public class xxx {
        private void onCamelContextStarted() {
            // Start managing the routes only when the camel context is started
            // so start/stop of managed routes do not clash with CamelContext
            // startup
            if (contextStarted.compareAndSet(false, true)) {

                // Eventually delay the startup of the routes a later time
                if (initialDelay.toMillis() > 0) {
                    LOG.debug("Policy will be effective in {}", initialDelay);
                    executorService.schedule(ClusteredRoutePolicy.this::onCamelContextStarted, initialDelay.toMillis(),
                            TimeUnit.MILLISECONDS);
                } else {
                    ClusteredRoutePolicy.this.onCamelContextStarted();
                }
            }
        }

};