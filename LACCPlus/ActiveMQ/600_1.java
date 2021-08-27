//,temp,JMSLargeMessageSendRecvTest.java,185,194,temp,JMSClientTest.java,928,937
//,2
public class xxx {
    private String createLargeString(int sizeInBytes) {
        byte[] base = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sizeInBytes; i++) {
            builder.append(base[i % base.length]);
        }

        LOG.debug("Created string with size : " + builder.toString().getBytes().length + " bytes");
        return builder.toString();
    }

};