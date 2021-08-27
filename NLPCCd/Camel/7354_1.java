//,temp,sample_4782.java,2,16,temp,sample_4772.java,2,16
//,3
public class xxx {
public void dummy_method(){
Exchange answer = null;
try {
byte[] completedLDBKey = keyBuilder(getRepositoryNameCompleted(), exchangeId);
byte[] rc = levelDBFile.getDb().get(completedLDBKey);
if (rc != null) {
answer = codec.unmarshallExchange(camelContext, new Buffer(rc));
}
} catch (IOException e) {
throw new RuntimeException("Error recovering exchangeId " + exchangeId + " from repository " + repositoryName, e);
}


log.info("recovering exchangeid");
}

};