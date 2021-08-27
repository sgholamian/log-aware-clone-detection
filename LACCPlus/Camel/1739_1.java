//,temp,JpaMessageIdRepository.java,243,271,temp,JpaMessageIdRepository.java,99,130
//,3
public class xxx {
            public Boolean doInTransaction(TransactionStatus status) {
                if (isJoinTransaction()) {
                    entityManager.joinTransaction();
                }
                try {
                    List<?> list = queryClear(entityManager);
                    if (!list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            Object item = it.next();
                            entityManager.remove(item);
                        }
                        entityManager.flush();
                        entityManager.close();
                    }
                    return Boolean.TRUE;
                } catch (Exception ex) {
                    LOG.error("Something went wrong trying to clear the repository {}", ex.getMessage(), ex);
                    throw new PersistenceException(ex);
                } finally {
                    try {
                        if (entityManager.isOpen()) {
                            entityManager.close();
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }

};