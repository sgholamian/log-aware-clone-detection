//,temp,sample_4522.java,2,13,temp,sample_4857.java,2,10
//,3
public class xxx {
public void shutdown() throws IOException {
IOException failure = null;
for (WALProvider provider: cached.values()) {
try {
provider.shutdown();
} catch (IOException e) {


log.info("problem shutting down wal provider");
}
}
}

};