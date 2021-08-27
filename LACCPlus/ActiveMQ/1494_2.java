//,temp,FanoutTransport.java,133,157,temp,FutureResponse.java,59,73
//,3
public class xxx {
    private InterruptedIOException dealWithInterrupt(InterruptedException e) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Operation interrupted: " + e, e);
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException(e.getMessage());
        interruptedIOException.initCause(e);
        try {
            if (transportFilter != null) {
                transportFilter.onException(interruptedIOException);
            }
        } finally {
            Thread.currentThread().interrupt();
        }
        return interruptedIOException;
    }

};