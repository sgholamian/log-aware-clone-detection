//,temp,ActiveMQConnection.java,1384,1424,temp,ActiveMQConnection.java,1321,1377
//,3
public class xxx {
    public Response syncSendPacket(Command command, int timeout) throws JMSException {
        if (isClosed()) {
            throw new ConnectionClosedException();
        } else {

            try {
                Response response = (Response)(timeout > 0
                        ? this.transport.request(command, timeout)
                        : this.transport.request(command));
                if (response.isException()) {
                    ExceptionResponse er = (ExceptionResponse)response;
                    if (er.getException() instanceof JMSException) {
                        throw (JMSException)er.getException();
                    } else {
                        if (isClosed() || closing.get()) {
                            LOG.debug("Received an exception but connection is closing");
                        }
                        JMSException jmsEx = null;
                        try {
                            jmsEx = JMSExceptionSupport.create(er.getException());
                        } catch(Throwable e) {
                            LOG.error("Caught an exception trying to create a JMSException for " +er.getException(),e);
                        }
                        if (er.getException() instanceof SecurityException && command instanceof ConnectionInfo){
                            try {
                                forceCloseOnSecurityException(er.getException());
                            } catch (Throwable t) {
                                // We throw the original error from the ExceptionResponse instead.
                            }
                        }
                        if (jmsEx != null) {
                            throw jmsEx;
                        }
                    }
                }
                return response;
            } catch (IOException e) {
                throw JMSExceptionSupport.create(e);
            }
        }
    }

};