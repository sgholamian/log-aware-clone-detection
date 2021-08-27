//,temp,ActiveMQConnection.java,1955,1975,temp,ActiveMQConnection.java,1934,1948
//,3
public class xxx {
    public void onClientInternalException(final Throwable error) {
        if ( !closed.get() && !closing.get() ) {
            if ( this.clientInternalExceptionListener != null ) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        ActiveMQConnection.this.clientInternalExceptionListener.onException(error);
                    }
                });
            } else {
                LOG.debug("Async client internal exception occurred with no exception listener registered: {}",
                        error, error);
            }
        }
    }

};