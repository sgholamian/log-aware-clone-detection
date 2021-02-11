//,temp,sample_4611.java,2,14,temp,sample_4610.java,2,11
//,3
public class xxx {
private static ByteBuffer configureTokens(Token<JobTokenIdentifier> jobToken, Credentials credentials, Map<String, ByteBuffer> serviceData) throws IOException {
Credentials taskCredentials = new Credentials(credentials);
TokenCache.setJobToken(jobToken, taskCredentials);
DataOutputBuffer containerTokens_dob = new DataOutputBuffer();
taskCredentials.writeTokenStorageToStream(containerTokens_dob);
ByteBuffer taskCredentialsBuffer = ByteBuffer.wrap(containerTokens_dob.getData(), 0, containerTokens_dob.getLength());


log.info("putting shuffle token in servicedata");
}

};