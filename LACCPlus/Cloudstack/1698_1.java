//,temp,NetworkHelperImpl.java,240,257,temp,NetScalerVMManagerImpl.java,406,422
//,3
public class xxx {
    @Override
    public VirtualRouter destroyRouter(final long routerId, final Account caller, final Long callerUserId) throws ResourceUnavailableException, ConcurrentOperationException {

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Attempting to destroy router " + routerId);
        }

        final DomainRouterVO router = _routerDao.findById(routerId);
        if (router == null) {
            return null;
        }

        _accountMgr.checkAccess(caller, null, true, router);

        _itMgr.expunge(router.getUuid());
        _routerDao.remove(router.getId());
        return router;
    }

};