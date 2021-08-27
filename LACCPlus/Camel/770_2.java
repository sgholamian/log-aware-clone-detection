//,temp,ClusteredRoutePolicy.java,382,397,temp,DefaultSupervisingRouteController.java,853,871
//,3
public class xxx {
        private void onCamelContextStarted() throws Exception {
            // Start managing the routes only when the camel context is started
            // so start/stop of managed routes do not clash with CamelContext
            // startup
            if (contextStarted.compareAndSet(false, true)) {
                // start non supervised routes first as if they fail then
                // camel context fails to start which is the behaviour of non-supervised routes
                startNonSupervisedRoutes();

                // Eventually delay the startup of the routes a later time
                if (initialDelay > 0) {
                    LOG.debug("Supervised routes will be started in {} millis", initialDelay);
                    executorService.schedule(DefaultSupervisingRouteController.this::startSupervisedRoutes, initialDelay,
                            TimeUnit.MILLISECONDS);
                } else {
                    startSupervisedRoutes();
                }
            }
        }

};