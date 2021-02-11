//,temp,ConfigurationServerImpl.java,1337,1355,temp,ConfigurationServerImpl.java,1308,1326
//,2
public class xxx {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {
                        _accountDao.lockRow(account.getId(), true);
                        List<ResourceCountVO> accountCounts = _resourceCountDao.listByOwnerId(account.getId(), ResourceOwnerType.Account);
                        List<String> accountCountStr = new ArrayList<String>();
                        for (ResourceCountVO accountCount : accountCounts) {
                            accountCountStr.add(accountCount.getType().toString());
                        }

                        if (accountCountStr.size() < accountExpectedCount) {
                            for (ResourceType resourceType : accountSupportedResourceTypes) {
                                if (!accountCountStr.contains(resourceType.toString())) {
                                    ResourceCountVO resourceCountVO = new ResourceCountVO(resourceType, 0, account.getId(), ResourceOwnerType.Account);
                                    s_logger.debug("Inserting resource count of type " + resourceType + " for account id=" + account.getId());
                                    _resourceCountDao.persist(resourceCountVO);
                                }
                            }
                        }
                    }

};