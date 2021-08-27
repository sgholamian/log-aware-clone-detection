//,temp,IOHelper.java,336,352,temp,MllpClientResource.java,133,151
//,3
public class xxx {
    public void reset() {
        try {
            clientSocket.setSoLinger(true, 0);
        } catch (SocketException socketEx) {
            log.warn("Exception encountered setting set SO_LINGER to force a TCP reset", socketEx);
        }
        try {
            if (null != inputStream) {
                clientSocket.close();
            }
        } catch (IOException e) {
            log.warn(String.format("Exception encountered resetting connection to {}:{}", mllpHost, mllpPort), e);
        } finally {
            inputStream = null;
            outputStream = null;
            clientSocket = null;
        }
        return;
    }

};