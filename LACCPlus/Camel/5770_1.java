//,temp,InternalRouteStartupManager.java,286,312,temp,InternalRouteStartupManager.java,78,101
//,3
public class xxx {
    void doWarmUpRoutes(Map<Integer, DefaultRouteStartupOrder> inputs, boolean autoStartup) throws FailedToStartRouteException {
        // now prepare the routes by starting its services before we start the
        // input
        for (Map.Entry<Integer, DefaultRouteStartupOrder> entry : inputs.entrySet()) {
            // defer starting inputs till later as we want to prepare the routes
            // by starting
            // all their processors and child services etc.
            // then later we open the floods to Camel by starting the inputs
            // what this does is to ensure Camel is more robust on starting
            // routes as all routes
            // will then be prepared in time before we start inputs which will
            // consume messages to be routed
            RouteService routeService = entry.getValue().getRouteService();
            StartupStep step = abstractCamelContext.getStartupStepRecorder().beginStep(Route.class, routeService.getId(),
                    "Warump Route");
            try {
                LOG.debug("Warming up route id: {} having autoStartup={}", routeService.getId(), autoStartup);
                setupRoute.set(routeService.getRoute());
                // ensure we setup before warmup
                routeService.setUp();
                routeService.warmUp();
            } finally {
                setupRoute.remove();
                abstractCamelContext.getStartupStepRecorder().endStep(step);
            }
        }
    }

};