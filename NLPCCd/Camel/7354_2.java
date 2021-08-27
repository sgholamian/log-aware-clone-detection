//,temp,sample_4782.java,2,16,temp,sample_4772.java,2,16
//,3
public class xxx {
public void dummy_method(){
Exchange answer = null;
try {
byte[] lDbKey = keyBuilder(repositoryName, key);
byte[] rc = levelDBFile.getDb().get(lDbKey);
if (rc != null) {
answer = codec.unmarshallExchange(camelContext, new Buffer(rc));
}
} catch (IOException e) {
throw new RuntimeException("Error getting key " + key + " from repository " + repositoryName, e);
}


log.info("getting key");
}

};