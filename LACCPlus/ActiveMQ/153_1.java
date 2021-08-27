//,temp,MQTTTest.java,884,900,temp,MQTTTest.java,808,824
//,3
public class xxx {
                @Override
                public void onReceive(MQTTFrame frame) {
                    LOG.debug("Client received:\n" + frame);
                    if (frame.messageType() == PUBLISH.TYPE) {
                        PUBLISH publish = new PUBLISH();
                        try {
                            publish.decode(frame);
                            LOG.info("PUBLISH " + publish);
                        } catch (ProtocolException e) {
                            fail("Error decoding publish " + e.getMessage());
                        }
                        if (publishMap.get(publish.messageId()) != null) {
                            assertTrue(publish.dup());
                        }
                        publishMap.put(publish.messageId(), publish);
                    }
                }

};