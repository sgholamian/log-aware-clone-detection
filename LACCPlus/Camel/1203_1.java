//,temp,ReplyManagerSupport.java,89,109,temp,RedeliveryErrorHandler.java,502,519
//,3
public class xxx {
    @Override
    public Destination getReplyTo() {
        if (replyTo != null) {
            return replyTo;
        }
        try {
            // the reply to destination has to be resolved using a DestinationResolver using
            // the MessageListenerContainer which occurs asynchronously so we have to wait
            // for that to happen before we can retrieve the reply to destination to be used
            log.trace("Waiting for replyTo to be set");
            boolean done = replyToLatch.await(replyToTimeout, TimeUnit.MILLISECONDS);
            if (!done) {
                log.warn("ReplyTo destination was not set and timeout occurred");
            } else {
                log.trace("Waiting for replyTo to be set done");
            }
        } catch (InterruptedException e) {
            // ignore
        }
        return replyTo;
    }

};