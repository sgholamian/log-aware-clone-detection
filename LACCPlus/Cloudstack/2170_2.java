//,temp,DedicatedResourceManagerImpl.java,511,607,temp,DedicatedResourceManagerImpl.java,398,509
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_DEDICATE_RESOURCE, eventDescription = "dedicating a Cluster")
    public List<DedicatedResourceVO> dedicateCluster(final Long clusterId, final Long domainId, final String accountName) {
        Long accountId = null;
        List<HostVO> hosts = null;
        if (accountName != null) {
            Account caller = CallContext.current().getCallingAccount();
            Account owner = _accountMgr.finalizeOwner(caller, accountName, domainId, null);
            accountId = owner.getId();
        }
        List<Long> childDomainIds = getDomainChildIds(domainId);
        childDomainIds.add(domainId);
        checkAccountAndDomain(accountId, domainId);
        ClusterVO cluster = _clusterDao.findById(clusterId);
        if (cluster == null) {
            throw new InvalidParameterValueException("Unable to find cluster by id " + clusterId);
        } else {
            DedicatedResourceVO dedicatedCluster = _dedicatedDao.findByClusterId(clusterId);
            DedicatedResourceVO dedicatedPodOfCluster = _dedicatedDao.findByPodId(cluster.getPodId());
            DedicatedResourceVO dedicatedZoneOfCluster = _dedicatedDao.findByZoneId(cluster.getDataCenterId());

            //check if cluster is dedicated
            if (dedicatedCluster != null) {
                s_logger.error("Cluster " + cluster.getName() + " is already dedicated");
                throw new CloudRuntimeException("Cluster " + cluster.getName() + " is already dedicated");
            }

            if (dedicatedPodOfCluster != null) {
                boolean domainIdInChildreanList = getDomainChildIds(dedicatedPodOfCluster.getDomainId()).contains(domainId);
                //can dedicate a cluster to an account/domain if pod is dedicated to parent-domain
                if (dedicatedPodOfCluster.getAccountId() != null || (accountId == null && !domainIdInChildreanList) ||
                    (accountId != null && !(dedicatedPodOfCluster.getDomainId().equals(domainId) || domainIdInChildreanList))) {
                    s_logger.error("Cannot dedicate Cluster. Its Pod is already dedicated");
                    HostPodVO pod = _podDao.findById(cluster.getPodId());
                    throw new CloudRuntimeException("Cluster's Pod " + pod.getName() + " is already dedicated");
                }
            }

            if (dedicatedZoneOfCluster != null) {
                boolean domainIdInChildreanList = getDomainChildIds(dedicatedZoneOfCluster.getDomainId()).contains(domainId);
                //can dedicate a cluster to an account/domain if zone is dedicated to parent-domain
                if (dedicatedZoneOfCluster.getAccountId() != null || (accountId == null && !domainIdInChildreanList) ||
                    (accountId != null && !(dedicatedZoneOfCluster.getDomainId().equals(domainId) || domainIdInChildreanList))) {
                    s_logger.error("Cannot dedicate Cluster. Its zone is already dedicated");
                    DataCenterVO zone = _zoneDao.findById(cluster.getDataCenterId());
                    throw new CloudRuntimeException("Cluster's Zone " + zone.getName() + " is already dedicated");
                }
            }

            //check if any resource under this cluster is dedicated to different account or sub-domain
            hosts = _hostDao.findByClusterId(cluster.getId());
            List<DedicatedResourceVO> hostsToRelease = new ArrayList<DedicatedResourceVO>();
            for (HostVO host : hosts) {
                DedicatedResourceVO dHost = _dedicatedDao.findByHostId(host.getId());
                if (dHost != null) {
                    if (!(childDomainIds.contains(dHost.getDomainId()))) {
                        throw new CloudRuntimeException("Host " + host.getName() + " under this Cluster " + cluster.getName() +
                            " is dedicated to different account/domain");
                    }
                    /*if all dedicated resources belongs to same account and domain then we should release dedication
                    and make new entry for this cluster */
                    if (accountId != null) {
                        if (dHost.getAccountId().equals(accountId)) {
                            hostsToRelease.add(dHost);
                        } else {
                            s_logger.error("Cannot dedicate Cluster " + cluster.getName() + " to account" + accountName);
                            throw new CloudRuntimeException("Cannot dedicate Cluster " + cluster.getName() + " to account" + accountName);
                        }
                    } else {
                        if (dHost.getAccountId() == null && dHost.getDomainId().equals(domainId)) {
                            hostsToRelease.add(dHost);
                        }
                    }
                }
            }

            for (DedicatedResourceVO dr : hostsToRelease) {
                releaseDedicatedResource(null, null, null, dr.getHostId());
            }
        }

        checkHostsSuitabilityForExplicitDedication(accountId, childDomainIds, hosts);

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
                DedicatedResourceVO dedicatedResource = new DedicatedResourceVO(null, null, clusterId, null, null, null, group.getId());
                try {
                    dedicatedResource.setDomainId(domainId);
                    if (accountIdFinal != null) {
                        dedicatedResource.setAccountId(accountIdFinal);
                    }
                    dedicatedResource = _dedicatedDao.persist(dedicatedResource);
                } catch (Exception e) {
                    s_logger.error("Unable to dedicate cluster due to " + e.getMessage(), e);
                    throw new CloudRuntimeException("Failed to dedicate cluster. Please contact Cloud Support.", e);
                }

                List<DedicatedResourceVO> result = new ArrayList<DedicatedResourceVO>();
                result.add(dedicatedResource);
                return result;
            }
        });
    }

};