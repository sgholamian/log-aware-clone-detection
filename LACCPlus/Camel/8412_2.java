//,temp,DefaultSupervisingRouteController.java,447,476,temp,DefaultSupervisingRouteController.java,421,445
//,3
public class xxx {
    private void startNonSupervisedRoutes() throws Exception {
        if (!isRunAllowed()) {
            return;
        }

        final List<String> routeList;

        synchronized (lock) {
            routeList = routes.stream()
                    .filter(r -> r.getStatus() == ServiceStatus.Stopped)
                    .filter(r -> !isSupervised(r.route))
                    .map(RouteHolder::getId)
                    .collect(Collectors.toList());
        }

        for (String route : routeList) {
            try {
                // let non supervising controller start the route by calling super
                LOG.debug("Starting non-supervised route {}", route);
                super.startRoute(route);
            } catch (Exception e) {
                throw new FailedToStartRouteException(route, e.getMessage(), e);
            }
        }
    }

};