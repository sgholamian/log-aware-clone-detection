//,temp,JpaMessageIdRepository.java,186,224,temp,JpaMessageIdRepository.java,143,178
//,3
public class xxx {
    @Override
    public boolean remove(final Exchange exchange, final String messageId) {
        final EntityManager entityManager
                = getTargetEntityManager(exchange, entityManagerFactory, true, sharedEntityManager, true);

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
                        MessageProcessed processed = (MessageProcessed) list.get(0);
                        entityManager.remove(processed);
                        entityManager.flush();
                        entityManager.close();
                        return Boolean.TRUE;
                    }
                } catch (Exception ex) {
                    LOG.error("Something went wrong trying to remove message to repository {}", ex.getMessage(), ex);
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

        LOG.debug("remove {}", messageId);
        return rc;
    }

};