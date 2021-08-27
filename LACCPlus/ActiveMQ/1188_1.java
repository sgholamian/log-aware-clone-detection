//,temp,MQTTTest.java,742,754,temp,MQTTTest.java,657,669
//,2
public class xxx {
            @Override
            public void onReceive(MQTTFrame frame) {
                LOG.debug("Client received:\n" + frame);
                if (frame.messageType() == PUBLISH.TYPE) {
                    PUBLISH publish = new PUBLISH();
                    try {
                        publish.decode(frame);
                    } catch (ProtocolException e) {
                        fail("Error decoding publish " + e.getMessage());
                    }
                    publishList.add(publish);
                }
            }

};