//,temp,DefaultCompositeSObjectCollectionsApiClient.java,213,226,temp,DefaultCompositeApiClient.java,256,273
//,3
public class xxx {
    <T> Optional<T> tryToReadResponse(final XStream xstream, final Class<T> expectedType, final InputStream responseStream) {
        if (responseStream == null) {
            return Optional.empty();
        }
        try {
            if (format == PayloadFormat.JSON) {
                return Optional.of(fromJson(expectedType, responseStream));
            }

            // must be XML
            return Optional.of(fromXml(xstream, responseStream));
        } catch (XStreamException | IOException e) {
            log.warn("Unable to read response from the Composite API", e);
            return Optional.empty();
        } finally {
            IOHelper.close(responseStream);
        }
    }

};