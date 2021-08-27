//,temp,InternalRouteStartupManager.java,286,312,temp,InternalRouteStartupManager.java,78,101
//,3
public class xxx {
    protected void doInitRoutes(Map<String, RouteService> routeServices)
            throws Exception {

        abstractCamelContext.setStartingRoutes(true);
        try {
            for (RouteService routeService : routeServices.values()) {
                StartupStep step = abstractCamelContext.getStartupStepRecorder().beginStep(Route.class, routeService.getId(),
                        "Init Route");
                try {
                    LOG.debug("Initializing route id: {}", routeService.getId());
                    setupRoute.set(routeService.getRoute());
                    // initializing route is called doSetup as we do not want to change the service state on the RouteService
                    // so it can remain as stopped, when Camel is booting as this was the previous behavior - otherwise its state
                    // would be initialized
                    routeService.setUp();
                } finally {
                    setupRoute.remove();
                    abstractCamelContext.getStartupStepRecorder().endStep(step);
                }
            }
        } finally {
            abstractCamelContext.setStartingRoutes(false);
        }
    }

};