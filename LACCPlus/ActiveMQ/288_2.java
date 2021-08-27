//,temp,ActiveMQConnection.java,1384,1424,temp,ActiveMQConnection.java,1321,1377
//,3
public class xxx {
    public void syncSendPacket(final Command command, final AsyncCallback onComplete) throws JMSException {
        if(onComplete==null) {
            syncSendPacket(command);
        } else {
            if (isClosed()) {
                throw new ConnectionClosedException();
            }
            try {
                this.transport.asyncRequest(command, new ResponseCallback() {
                    @Override
                    public void onCompletion(FutureResponse resp) {
                        Response response;
                        Throwable exception = null;
                        try {
                            response = resp.getResult();
                            if (response.isException()) {
                                ExceptionResponse er = (ExceptionResponse)response;
                                exception = er.getException();
                            }
                        } catch (Exception e) {
                            exception = e;
                        }
                        if (exception != null) {
                            if ( exception instanceof JMSException) {
                                onComplete.onException((JMSException) exception);
                            } else {
                                if (isClosed() || closing.get()) {
                                    LOG.debug("Received an exception but connection is closing");
                                }
                                JMSException jmsEx = null;
                                try {
                                    jmsEx = JMSExceptionSupport.create(exception);
                                } catch(Throwable e) {
                                    LOG.error("Caught an exception trying to create a JMSException for " +exception,e);
                                }
                                // dispose of transport for security exceptions on connection initiation
                                if (exception instanceof SecurityException && command instanceof ConnectionInfo){
                                    try {
                                        forceCloseOnSecurityException(exception);
                                    } catch (Throwable t) {
                                        // We throw the original error from the ExceptionResponse instead.
                                    }
                                }
                                if (jmsEx != null) {
                                    onComplete.onException(jmsEx);
                                }
                            }
                        } else {
                            onComplete.onSuccess();
                        }
                    }
                });
            } catch (IOException e) {
                throw JMSExceptionSupport.create(e);
            }
        }
    }

};