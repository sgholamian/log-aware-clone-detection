//,temp,MllpClientResource.java,266,310,temp,MllpClientResource.java,221,260
//,3
public class xxx {
    public void sendFramedData(String hl7Message, boolean disconnectAfterSend) {
        if (null == clientSocket) {
            this.connect();
        }

        if (!clientSocket.isConnected()) {
            throw new MllpJUnitResourceException("Cannot send message - client is not connected");
        }
        if (null == outputStream) {
            throw new MllpJUnitResourceException("Cannot send message - output stream is null");
        }
        byte[] payloadBytes = hl7Message.getBytes();
        try {
            if (sendStartOfBlock) {
                outputStream.write(START_OF_BLOCK);
            } else {
                log.warn("Not sending START_OF_BLOCK");
            }
            outputStream.write(payloadBytes, 0, payloadBytes.length);
            if (sendEndOfBlock) {
                outputStream.write(END_OF_BLOCK);
            } else {
                log.warn("Not sending END_OF_BLOCK");
            }
            if (sendEndOfData) {
                outputStream.write(END_OF_DATA);
            } else {
                log.warn("Not sending END_OF_DATA");
            }
            outputStream.flush();
        } catch (IOException e) {
            log.error("Unable to send HL7 message", e);
            throw new MllpJUnitResourceException("Unable to send HL7 message", e);
        }

        if (disconnectAfterSend) {
            log.warn("Closing TCP connection");
            disconnect();
        }
    }

};