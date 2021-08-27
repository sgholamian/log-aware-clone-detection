//,temp,ActiveMQConnection.java,1955,1975,temp,ActiveMQConnection.java,1934,1948
//,3
public class xxx {
    public void onAsyncException(Throwable error) {
        if (!closed.get() && !closing.get()) {
            if (this.exceptionListener != null) {

                if (!(error instanceof JMSException)) {
                    error = JMSExceptionSupport.create(error);
                }
                final JMSException e = (JMSException)error;

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        ActiveMQConnection.this.exceptionListener.onException(e);
                    }
                });

            } else {
                LOG.debug("Async exception with no exception listener: {}", error, error);
            }
        }
    }

};