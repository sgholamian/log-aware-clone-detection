//,temp,FirewallManagerImpl.java,825,855,temp,VpcManagerImpl.java,2183,2199
//,3
public class xxx {
    protected void markStaticRouteForRevoke(final StaticRouteVO route, final Account caller) {
        s_logger.debug("Revoking static route " + route);
        if (caller != null) {
            _accountMgr.checkAccess(caller, null, false, route);
        }

        if (route.getState() == StaticRoute.State.Staged) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Found a static route that is still in stage state so just removing it: " + route);
            }
            _staticRouteDao.remove(route.getId());
        } else if (route.getState() == StaticRoute.State.Add || route.getState() == StaticRoute.State.Active) {
            route.setState(StaticRoute.State.Revoke);
            _staticRouteDao.update(route.getId(), route);
            s_logger.debug("Marked static route " + route + " with state " + StaticRoute.State.Revoke);
        }
    }

};