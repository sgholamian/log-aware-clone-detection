//,temp,DedicatedResourceManagerImpl.java,511,607,temp,DedicatedResourceManagerImpl.java,398,509
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_DEDICATE_RESOURCE, eventDescription = "dedicating a Host")
    public List<DedicatedResourceVO> dedicateHost(final Long hostId, final Long domainId, final String accountName) {
        Long accountId = null;
        if (accountName != null) {
            Account caller = CallContext.current().getCallingAccount();
            Account owner = _accountMgr.finalizeOwner(caller, accountName, domainId, null);
            accountId = owner.getId();
        }
        checkAccountAndDomain(accountId, domainId);
        HostVO host = _hostDao.findById(hostId);
        if (host == null) {
            throw new InvalidParameterValueException("Unable to find host by id " + hostId);
        } else {
            //check if host is of routing type
            if (host.getType() != Host.Type.Routing) {
                throw new CloudRuntimeException("Invalid host type for host " + host.getName());
            }

            DedicatedResourceVO dedicatedHost = _dedicatedDao.findByHostId(hostId);
            DedicatedResourceVO dedicatedClusterOfHost = _dedicatedDao.findByClusterId(host.getClusterId());
            DedicatedResourceVO dedicatedPodOfHost = _dedicatedDao.findByPodId(host.getPodId());
            DedicatedResourceVO dedicatedZoneOfHost = _dedicatedDao.findByZoneId(host.getDataCenterId());

            if (dedicatedHost != null) {
                s_logger.error("Host " + host.getName() + " is already dedicated");
                throw new CloudRuntimeException("Host " + host.getName() + " is already dedicated");
            }

            if (dedicatedClusterOfHost != null) {
                boolean domainIdInChildreanList = getDomainChildIds(dedicatedClusterOfHost.getDomainId()).contains(domainId);
                //can dedicate a host to an account/domain if cluster is dedicated to parent-domain
                if (dedicatedClusterOfHost.getAccountId() != null || (accountId == null && !domainIdInChildreanList) ||
                    (accountId != null && !(dedicatedClusterOfHost.getDomainId().equals(domainId) || domainIdInChildreanList))) {
                    ClusterVO cluster = _clusterDao.findById(host.getClusterId());
                    s_logger.error("Host's Cluster " + cluster.getName() + " is already dedicated");
                    throw new CloudRuntimeException("Host's Cluster " + cluster.getName() + " is already dedicated");
                }
            }

            if (dedicatedPodOfHost != null) {
                boolean domainIdInChildreanList = getDomainChildIds(dedicatedPodOfHost.getDomainId()).contains(domainId);
                //can dedicate a host to an account/domain if pod is dedicated to parent-domain
                if (dedicatedPodOfHost.getAccountId() != null || (accountId == null && !domainIdInChildreanList) ||
                    (accountId != null && !(dedicatedPodOfHost.getDomainId().equals(domainId) || domainIdInChildreanList))) {
                    HostPodVO pod = _podDao.findById(host.getPodId());
                    s_logger.error("Host's Pod " + pod.getName() + " is already dedicated");
                    throw new CloudRuntimeException("Host's Pod " + pod.getName() + " is already dedicated");
                }
            }

            if (dedicatedZoneOfHost != null) {
                boolean domainIdInChildreanList = getDomainChildIds(dedicatedZoneOfHost.getDomainId()).contains(domainId);
                //can dedicate a host to an account/domain if zone is dedicated to parent-domain
                if (dedicatedZoneOfHost.getAccountId() != null || (accountId == null && !domainIdInChildreanList) ||
                    (accountId != null && !(dedicatedZoneOfHost.getDomainId().equals(domainId) || domainIdInChildreanList))) {
                    DataCenterVO zone = _zoneDao.findById(host.getDataCenterId());
                    s_logger.error("Host's Data Center " + zone.getName() + " is already dedicated");
                    throw new CloudRuntimeException("Host's Data Center " + zone.getName() + " is already dedicated");
                }
            }
        }

        List<Long> childDomainIds = getDomainChildIds(domainId);
        childDomainIds.add(domainId);
        checkHostSuitabilityForExplicitDedication(accountId, childDomainIds, hostId);

        final Long accountIdFinal = accountId;
        return Transaction.execute(new TransactionCallback<List<DedicatedResourceVO>>() {
            @Override
            public List<DedicatedResourceVO> doInTransaction(TransactionStatus status) {
                // find or create the affinity group by name under this account/domain
                AffinityGroup group = findOrCreateDedicatedAffinityGroup(domainId, accountIdFinal);
                if (group == null) {
                    s_logger.error("Unable to dedicate zone due to, failed to create dedication affinity group");
                    throw new CloudRuntimeException("Failed to dedicate zone. Please contact Cloud Support.");
                }
                DedicatedResourceVO dedicatedResource = new DedicatedResourceVO(null, null, null, hostId, null, null, group.getId());
                try {
                    dedicatedResource.setDomainId(domainId);
                    if (accountIdFinal != null) {
                        dedicatedResource.setAccountId(accountIdFinal);
                    }
                    dedicatedResource = _dedicatedDao.persist(dedicatedResource);
                } catch (Exception e) {
                    s_logger.error("Unable to dedicate host due to " + e.getMessage(), e);
                    throw new CloudRuntimeException("Failed to dedicate host. Please contact Cloud Support.", e);
                }

                List<DedicatedResourceVO> result = new ArrayList<DedicatedResourceVO>();
                result.add(dedicatedResource);
                return result;
            }
        });

    }

};