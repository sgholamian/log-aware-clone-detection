//,temp,MQTTAuthTest.java,330,344,temp,MQTTAuthTest.java,289,303
//,2
public class xxx {
            @Override
            public void onReceive(MQTTFrame frame) {
                LOG.info("Client received: {}", frame);
                if (frame.messageType() == CONNACK.TYPE) {
                    CONNACK connAck = new CONNACK();
                    try {
                        connAck.decode(frame);
                        LOG.info("{}", connAck);
                        errorCode.set(connAck.code().ordinal());
                        assertEquals(CONNACK.Code.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD, connAck.code());
                    } catch (ProtocolException e) {
                        fail("Error decoding publish " + e.getMessage());
                    }
                }
            }

};