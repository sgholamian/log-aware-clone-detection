//,temp,MllpClientResource.java,408,422,temp,MllpClientResource.java,385,402
//,3
public class xxx {
    public String receiveData(int timeout) throws SocketException, SocketTimeoutException {
        clientSocket.setSoTimeout(timeout);
        StringBuilder availableInput = new StringBuilder();

        try {
            do {
                availableInput.append((char) inputStream.read());
            } while (0 < inputStream.available());
        } catch (SocketTimeoutException timeoutEx) {
            log.error("Timeout while receiving available input", timeoutEx);
            throw new MllpJUnitResourceTimeoutException("Timeout while receiving available input", timeoutEx);
        } catch (IOException e) {
            log.warn("Exception encountered eating available input", e);
            throw new MllpJUnitResourceException("Exception encountered eating available input", e);
        }

        return availableInput.toString();
    }

};