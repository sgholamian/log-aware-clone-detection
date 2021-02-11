//,temp,UsageManagerImpl.java,1705,1748,temp,UsageManagerImpl.java,1377,1412
//,3
public class xxx {
    private void createNetworkOfferingEvent(UsageEventVO event) {

        long zoneId = -1L;

        long vmId = event.getResourceId();
        long networkOfferingId = event.getOfferingId();
        long nicId = 0;
        try {
            nicId = Long.parseLong(event.getResourceName());
        } catch (Exception e) {
            s_logger.warn("failed to get nic id from resource name, resource name is: " + event.getResourceName());
        }

        if (EventTypes.EVENT_NETWORK_OFFERING_CREATE.equals(event.getType()) || EventTypes.EVENT_NETWORK_OFFERING_ASSIGN.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Creating networking offering: " + networkOfferingId + " for Vm: " + vmId + " for account: " + event.getAccountId());
            }
            zoneId = event.getZoneId();
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            boolean isDefault = (event.getSize() == 1) ? true : false;
            UsageNetworkOfferingVO networkOffering =
                    new UsageNetworkOfferingVO(zoneId, event.getAccountId(), acct.getDomainId(), vmId, networkOfferingId, nicId, isDefault, event.getCreateDate(), null);
            _usageNetworkOfferingDao.persist(networkOffering);
        } else if (EventTypes.EVENT_NETWORK_OFFERING_DELETE.equals(event.getType()) || EventTypes.EVENT_NETWORK_OFFERING_REMOVE.equals(event.getType())) {
            SearchCriteria<UsageNetworkOfferingVO> sc = _usageNetworkOfferingDao.createSearchCriteria();
            sc.addAnd("accountId", SearchCriteria.Op.EQ, event.getAccountId());
            sc.addAnd("vmInstanceId", SearchCriteria.Op.EQ, vmId);
            sc.addAnd("nicId", SearchCriteria.Op.EQ, nicId);
            sc.addAnd("networkOfferingId", SearchCriteria.Op.EQ, networkOfferingId);
            sc.addAnd("deleted", SearchCriteria.Op.NULL);
            List<UsageNetworkOfferingVO> noVOs = _usageNetworkOfferingDao.search(sc, null);
            if (noVOs.size() > 1) {
                s_logger.warn("More that one usage entry for networking offering: " + networkOfferingId + " for Vm: " + vmId + " assigned to account: " +
                        event.getAccountId() + "; marking them all as deleted...");
            }
            for (UsageNetworkOfferingVO noVO : noVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting network offering: " + noVO.getNetworkOfferingId() + " from Vm: " + noVO.getVmInstanceId());
                }
                noVO.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usageNetworkOfferingDao.update(noVO);
            }
        }
    }

};