//,temp,FanoutTransport.java,133,157,temp,FutureResponse.java,59,73
//,3
public class xxx {
        @Override
        public void onException(IOException error) {
            try {
                synchronized (reconnectMutex) {
                    if (transport == null || !transport.isConnected()) {
                        return;
                    }

                    LOG.debug("Transport failed, starting up reconnect task", error);

                    ServiceSupport.dispose(transport);
                    transport = null;
                    connectedCount--;
                    if (primary == this) {
                        primary = null;
                    }
                    reconnectTask.wakeup();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (transportListener != null) {
                    transportListener.onException(new InterruptedIOException());
                }
            }
        }

};