//,temp,DefaultSupervisingRouteController.java,447,476,temp,DefaultSupervisingRouteController.java,421,445
//,3
public class xxx {
    private void startSupervisedRoutes() {
        if (!isRunAllowed()) {
            return;
        }

        final List<String> routeList;

        synchronized (lock) {
            routeList = routes.stream()
                    .filter(r -> r.getStatus() == ServiceStatus.Stopped)
                    .filter(r -> isSupervised(r.route))
                    .map(RouteHolder::getId)
                    .collect(Collectors.toList());
        }

        LOG.debug("Starting {} supervised routes", routeList.size());
        for (String route : routeList) {
            try {
                startRoute(route);
            } catch (Exception e) {
                // ignored, exception handled by startRoute
            }
        }

        if (getCamelContext().getStartupSummaryLevel() != StartupSummaryLevel.Off
                && getCamelContext().getStartupSummaryLevel() != StartupSummaryLevel.Oneline) {
            // log after first round of attempts
            logRouteStartupSummary();
        }
    }

};