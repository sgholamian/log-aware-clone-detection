//,temp,DedicatedResourceManagerImpl.java,581,604,temp,DedicatedResourceManagerImpl.java,235,267
//,3
public class xxx {
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

};