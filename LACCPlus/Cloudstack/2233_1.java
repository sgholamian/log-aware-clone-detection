//,temp,LoadBalanceRuleHandler.java,478,482,temp,VirtualNetworkApplianceManagerImpl.java,2192,2202
//,3
public class xxx {
    protected DomainRouterVO start(final DomainRouterVO elbVm, final Map<Param, Object> params) throws ConcurrentOperationException {
        s_logger.debug("Starting ELB VM " + elbVm);
        _itMgr.start(elbVm.getUuid(), params);
        return _routerDao.findById(elbVm.getId());
    }

};