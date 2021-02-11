//,temp,sample_8469.java,2,17,temp,sample_8473.java,2,17
//,2
public class xxx {
public void dummy_method(){
pm.setEntity(new StringEntity(convertToString(newObject), ContentType.APPLICATION_XML));
final HttpResponse response = executeMethod(pm);
if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT) {
String errorMessage;
try {
errorMessage = responseToErrorMessage(response);
} catch (final IOException e) {
throw new BrocadeVcsApiException("Failed to update object : " + e.getMessage());
}
pm.releaseConnection();


log.info("failed to update object");
}
}

};