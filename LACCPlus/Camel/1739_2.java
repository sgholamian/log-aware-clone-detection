//,temp,JpaMessageIdRepository.java,243,271,temp,JpaMessageIdRepository.java,99,130
//,3
public class xxx {
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

};