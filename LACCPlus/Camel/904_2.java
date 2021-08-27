//,temp,JpaMessageIdRepository.java,192,219,temp,JpaMessageIdRepository.java,93,135
//,3
public class xxx {
    @Override
    public boolean add(final Exchange exchange, final String messageId) {
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
                        MessageProcessed processed = new MessageProcessed();
                        processed.setProcessorName(processorName);
                        processed.setMessageId(messageId);
                        processed.setCreatedAt(new Date());
                        entityManager.persist(processed);
                        entityManager.flush();
                        entityManager.close();
                        return Boolean.TRUE;
                    } else {
                        return Boolean.FALSE;
                    }
                } catch (Exception ex) {
                    LOG.error("Something went wrong trying to add message to repository {}", ex.getMessage(), ex);
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

        LOG.debug("add {} -> {}", messageId, rc);
        return rc;
    }

};