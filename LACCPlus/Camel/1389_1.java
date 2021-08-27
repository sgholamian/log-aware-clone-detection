//,temp,DefaultCompositeSObjectCollectionsApiClient.java,213,226,temp,DefaultCompositeApiClient.java,256,273
//,3
public class xxx {
    private <T> Optional<List<T>> tryToReadListResponse(
            final Class<T> expectedType, final InputStream responseStream) {
        if (responseStream == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(fromJsonList(expectedType, responseStream));
        } catch (IOException e) {
            log.warn("Unable to read response from the Composite API", e);
            return Optional.empty();
        } finally {
            IOHelper.close(responseStream);
        }
    }

};