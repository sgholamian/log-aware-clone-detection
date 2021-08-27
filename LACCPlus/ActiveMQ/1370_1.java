//,temp,DestinationInterceptorDurableSubTest.java,251,259,temp,WebClient.java,233,238
//,3
public class xxx {
                  public void send(ProducerBrokerExchange context, Message message) throws Exception {
                    // Send message to Destination
                    if (LOG.isDebugEnabled()) {
                      LOG.debug("SimpleDestinationInterceptor: Sending message to destination:"
                          + this.getActiveMQDestination().getPhysicalName());
                    }
                    // message.setDestination(destination.getActiveMQDestination());
                    super.send(context, message);
                  }

};