//,temp,SjmsProducer.java,96,141,temp,JmsProducer.java,77,122
//,2
public class xxx {
    protected void initReplyManager() {
        if (!started.get()) {
            synchronized (this) {
                if (started.get()) {
                    return;
                }

                // must use the classloader from the application context when creating reply manager,
                // as it should inherit the classloader from app context and not the current which may be
                // a different classloader
                ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
                ClassLoader ac = endpoint.getCamelContext().getApplicationContextClassLoader();
                try {
                    if (ac != null) {
                        Thread.currentThread().setContextClassLoader(ac);
                    }
                    // validate that replyToType and replyTo is configured accordingly
                    if (endpoint.getReplyToType() != null) {
                        // setting temporary with a fixed replyTo is not supported
                        if (endpoint.getReplyTo() != null && endpoint.getReplyToType().equals(ReplyToType.Temporary.name())) {
                            throw new IllegalArgumentException(
                                    "ReplyToType " + ReplyToType.Temporary
                                                               + " is not supported when replyTo " + endpoint.getReplyTo()
                                                               + " is also configured.");
                        }
                    }

                    if (endpoint.getReplyTo() != null) {
                        replyManager = createReplyManager(endpoint.getReplyTo());
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Using JmsReplyManager: {} to process replies from: {}", replyManager,
                                    endpoint.getReplyTo());
                        }
                    } else {
                        replyManager = createReplyManager();
                        LOG.debug("Using JmsReplyManager: {} to process replies from temporary queue", replyManager);
                    }
                } catch (Exception e) {
                    throw new FailedToCreateProducerException(endpoint, e);
                } finally {
                    Thread.currentThread().setContextClassLoader(oldClassLoader);
                }
                started.set(true);
            }
        }
    }

};