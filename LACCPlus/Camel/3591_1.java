//,temp,MllpClientResource.java,408,422,temp,MllpClientResource.java,385,402
//,3
public class xxx {
    public String eatData(int timeout) throws SocketException {
        clientSocket.setSoTimeout(timeout);

        StringBuilder availableInput = new StringBuilder();
        try {
            while (0 < inputStream.available()) {
                availableInput.append((char) inputStream.read());
            }
        } catch (IOException e) {
            log.warn("Exception encountered eating available input", e);
            throw new MllpJUnitResourceException("Exception encountered eating available input", e);
        }

        return availableInput.toString();
    }

};