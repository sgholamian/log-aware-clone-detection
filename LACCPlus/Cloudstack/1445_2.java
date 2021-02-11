//,temp,DedicatedResourceManagerImpl.java,511,607,temp,DedicatedResourceManagerImpl.java,271,396
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_DEDICATE_RESOURCE, eventDescription = "dedicating a Pod")
    public List<DedicatedResourceVO> dedicatePod(final Long podId, final Long domainId, final String accountName) {
        Long accountId = null;
        if (accountName != null) {
            Account caller = CallContext.current().getCallingAccount();
            Account owner = _accountMgr.finalizeOwner(caller, accountName, domainId, null);
            accountId = owner.getId();
        }
        List<Long> childDomainIds = getDomainChildIds(domainId);
        childDomainIds.add(domainId);
        checkAccountAndDomain(accountId, domainId);
        HostPodVO pod = _podDao.findById(podId);
        List<HostVO> hosts = null;
        if (pod == null) {
            throw new InvalidParameterValueException("Unable to find pod by id " + podId);
        } else {
            DedicatedResourceVO dedicatedPod = _dedicatedDao.findByPodId(podId);
            DedicatedResourceVO dedicatedZoneOfPod = _dedicatedDao.findByZoneId(pod.getDataCenterId());
            //check if pod is dedicated
            if (dedicatedPod != null) {
                s_logger.error("Pod " + pod.getName() + " is already dedicated");
                throw new CloudRuntimeException("Pod " + pod.getName() + " is already dedicated");
            }

            if (dedicatedZoneOfPod != null) {
                boolean domainIdInChildreanList = getDomainChildIds(dedicatedZoneOfPod.getDomainId()).contains(domainId);
                //can dedicate a pod to an account/domain if zone is dedicated to parent-domain
                if (dedicatedZoneOfPod.getAccountId() != null || (accountId == null && !domainIdInChildreanList) ||
                    (accountId != null && !(dedicatedZoneOfPod.getDomainId().equals(domainId) || domainIdInChildreanList))) {
                    DataCenterVO zone = _zoneDao.findById(pod.getDataCenterId());
                    s_logger.error("Cannot dedicate Pod. Its zone is already dedicated");
                    throw new CloudRuntimeException("Pod's Zone " + zone.getName() + " is already dedicated");
                }
            }

            //check if any resource under this pod is dedicated to different account or sub-domain
            List<ClusterVO> clusters = _clusterDao.listByPodId(pod.getId());
            List<DedicatedResourceVO> clustersToRelease = new ArrayList<DedicatedResourceVO>();
            List<DedicatedResourceVO> hostsToRelease = new ArrayList<DedicatedResourceVO>();
            for (ClusterVO cluster : clusters) {
                DedicatedResourceVO dCluster = _dedicatedDao.findByClusterId(cluster.getId());
                if (dCluster != null) {
                    if (!(childDomainIds.contains(dCluster.getDomainId()))) {
                        throw new CloudRuntimeException("Cluster " + cluster.getName() + " under this Pod " + pod.getName() + " is dedicated to different account/domain");
                    }
                    /*if all dedicated resources belongs to same account and domain then we should release dedication
                    and make new entry for this Pod*/
                    if (accountId != null) {
                        if (dCluster.getAccountId().equals(accountId)) {
                            clustersToRelease.add(dCluster);
                        } else {
                            s_logger.error("Cluster " + cluster.getName() + " under this Pod " + pod.getName() + " is dedicated to different account/domain");
                            throw new CloudRuntimeException("Cluster " + cluster.getName() + " under this Pod " + pod.getName() +
                                " is dedicated to different account/domain");
                        }
                    } else {
                        if (dCluster.getAccountId() == null && dCluster.getDomainId().equals(domainId)) {
                            clustersToRelease.add(dCluster);
                        }
                    }
                }
            }

            for (DedicatedResourceVO dr : clustersToRelease) {
                releaseDedicatedResource(null, null, dr.getClusterId(), null);
            }

            hosts = _hostDao.findByPodId(pod.getId());
            for (HostVO host : hosts) {
                DedicatedResourceVO dHost = _dedicatedDao.findByHostId(host.getId());
                if (dHost != null) {
                    if (!(getDomainChildIds(domainId).contains(dHost.getDomainId()))) {
                        throw new CloudRuntimeException("Host " + host.getName() + " under this Pod " + pod.getName() + " is dedicated to different account/domain");
                    }
                    if (accountId != null) {
                        if (dHost.getAccountId().equals(accountId)) {
                            hostsToRelease.add(dHost);
                        } else {
                            s_logger.error("Host " + host.getName() + " under this Pod " + pod.getName() + " is dedicated to different account/domain");
                            throw new CloudRuntimeException("Host " + host.getName() + " under this Pod " + pod.getName() + " is dedicated to different account/domain");
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
                DedicatedResourceVO dedicatedResource = new DedicatedResourceVO(null, podId, null, null, null, null, group.getId());
                try {
                    dedicatedResource.setDomainId(domainId);
                    if (accountIdFinal != null) {
                        dedicatedResource.setAccountId(accountIdFinal);
                    }
                    dedicatedResource = _dedicatedDao.persist(dedicatedResource);
                } catch (Exception e) {
                    s_logger.error("Unable to dedicate pod due to " + e.getMessage(), e);
                    throw new CloudRuntimeException("Failed to dedicate pod. Please contact Cloud Support.");
                }

                List<DedicatedResourceVO> result = new ArrayList<DedicatedResourceVO>();
                result.add(dedicatedResource);
                return result;
            }
        });
    }

};