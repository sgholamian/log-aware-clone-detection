//,temp,JpaMessageIdRepository.java,186,224,temp,JpaMessageIdRepository.java,143,178
//,3
public class xxx {
    @Override
    public boolean contains(final Exchange exchange, final String messageId) {
        final EntityManager entityManager
                = getTargetEntityManager(exchange, entityManagerFactory, true, sharedEntityManager, true);

        // Run this in single transaction.
        Boolean rc = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            public Boolean doInTransaction(TransactionStatus status) {
                if (isJoinTransaction()) {
                    entityManager.joinTransaction();
                }
                try {
                    List<?> list = query(entityManager, messageId);
                    if (list.isEmpty()) {
                        return Boolean.FALSE;
                    } else {
                        return Boolean.TRUE;
                    }
                } catch (Exception ex) {
                    LOG.error("Something went wrong trying to check message in repository {}", ex.getMessage(), ex);
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
        });

        LOG.debug("contains {} -> {}", messageId, rc);
        return rc;
    }

};