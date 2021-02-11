//,temp,DedicatedResourceManagerImpl.java,511,607,temp,DedicatedResourceManagerImpl.java,122,269
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_DEDICATE_RESOURCE, eventDescription = "dedicating a Zone")
    public List<DedicatedResourceVO> dedicateZone(final Long zoneId, final Long domainId, final String accountName) {
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
        final DataCenterVO dc = _zoneDao.findById(zoneId);
        if (dc == null) {
            throw new InvalidParameterValueException("Unable to find zone by id " + zoneId);
        } else {
            DedicatedResourceVO dedicatedZone = _dedicatedDao.findByZoneId(zoneId);
            //check if zone is dedicated
            if (dedicatedZone != null) {
                s_logger.error("Zone " + dc.getName() + " is already dedicated");
                throw new CloudRuntimeException("Zone  " + dc.getName() + " is already dedicated");
            }

            //check if any resource under this zone is dedicated to different account or sub-domain
            List<HostPodVO> pods = _podDao.listByDataCenterId(dc.getId());
            List<DedicatedResourceVO> podsToRelease = new ArrayList<DedicatedResourceVO>();
            List<DedicatedResourceVO> clustersToRelease = new ArrayList<DedicatedResourceVO>();
            List<DedicatedResourceVO> hostsToRelease = new ArrayList<DedicatedResourceVO>();
            for (HostPodVO pod : pods) {
                DedicatedResourceVO dPod = _dedicatedDao.findByPodId(pod.getId());
                if (dPod != null) {
                    if (!(childDomainIds.contains(dPod.getDomainId()))) {
                        throw new CloudRuntimeException("Pod " + pod.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
                    }
                    if (accountId != null) {
                        if (dPod.getAccountId().equals(accountId)) {
                            podsToRelease.add(dPod);
                        } else {
                            s_logger.error("Pod " + pod.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
                            throw new CloudRuntimeException("Pod " + pod.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
                        }
                    } else {
                        if (dPod.getAccountId() == null && dPod.getDomainId().equals(domainId)) {
                            podsToRelease.add(dPod);
                        }
                    }
                }
            }

            for (DedicatedResourceVO dr : podsToRelease) {
                releaseDedicatedResource(null, dr.getPodId(), null, null);
            }

            List<ClusterVO> clusters = _clusterDao.listClustersByDcId(dc.getId());
            for (ClusterVO cluster : clusters) {
                DedicatedResourceVO dCluster = _dedicatedDao.findByClusterId(cluster.getId());
                if (dCluster != null) {
                    if (!(childDomainIds.contains(dCluster.getDomainId()))) {
                        throw new CloudRuntimeException("Cluster " + cluster.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
                    }
                    if (accountId != null) {
                        if (dCluster.getAccountId().equals(accountId)) {
                            clustersToRelease.add(dCluster);
                        } else {
                            s_logger.error("Cluster " + cluster.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
                            throw new CloudRuntimeException("Cluster " + cluster.getName() + " under this Zone " + dc.getName() +
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

            hosts = _hostDao.listByDataCenterId(dc.getId());
            for (HostVO host : hosts) {
                DedicatedResourceVO dHost = _dedicatedDao.findByHostId(host.getId());
                if (dHost != null) {
                    if (!(childDomainIds.contains(dHost.getDomainId()))) {
                        throw new CloudRuntimeException("Host " + host.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
                    }
                    if (accountId != null) {
                        if (dHost.getAccountId().equals(accountId)) {
                            hostsToRelease.add(dHost);
                        } else {
                            s_logger.error("Host " + host.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
                            throw new CloudRuntimeException("Host " + host.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
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

                DedicatedResourceVO dedicatedResource = new DedicatedResourceVO(zoneId, null, null, null, null, null, group.getId());
                try {
                    dedicatedResource.setDomainId(domainId);
                    if (accountIdFinal != null) {
                        dedicatedResource.setAccountId(accountIdFinal);
                    }
                    dedicatedResource = _dedicatedDao.persist(dedicatedResource);

                    // save the domainId in the zone
                    dc.setDomainId(domainId);
                    if (!_zoneDao.update(zoneId, dc)) {
                        throw new CloudRuntimeException("Failed to dedicate zone, could not set domainId. Please contact Cloud Support.");
                    }

                } catch (Exception e) {
                    s_logger.error("Unable to dedicate zone due to " + e.getMessage(), e);
                    throw new CloudRuntimeException("Failed to dedicate zone. Please contact Cloud Support.");
                }

                List<DedicatedResourceVO> result = new ArrayList<DedicatedResourceVO>();
                result.add(dedicatedResource);
                return result;

            }
        });
    }

};