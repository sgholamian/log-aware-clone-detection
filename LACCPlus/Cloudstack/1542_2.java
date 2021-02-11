//,temp,ConfigurationServerImpl.java,1337,1355,temp,ConfigurationServerImpl.java,1308,1326
//,2
public class xxx {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {
                        _domainDao.lockRow(domain.getId(), true);
                        List<ResourceCountVO> domainCounts = _resourceCountDao.listByOwnerId(domain.getId(), ResourceOwnerType.Domain);
                        List<String> domainCountStr = new ArrayList<String>();
                        for (ResourceCountVO domainCount : domainCounts) {
                            domainCountStr.add(domainCount.getType().toString());
                        }

                        if (domainCountStr.size() < domainExpectedCount) {
                            for (ResourceType resourceType : domainSupportedResourceTypes) {
                                if (!domainCountStr.contains(resourceType.toString())) {
                                    ResourceCountVO resourceCountVO = new ResourceCountVO(resourceType, 0, domain.getId(), ResourceOwnerType.Domain);
                                    s_logger.debug("Inserting resource count of type " + resourceType + " for domain id=" + domain.getId());
                                    _resourceCountDao.persist(resourceCountVO);
                                }
                            }
                        }
                    }

};