//,temp,sample_4524.java,2,13,temp,sample_4874.java,2,11
//,3
public class xxx {
public void close() throws IOException {
IOException failure = null;
for (WALProvider provider : cached.values()) {
try {
provider.close();
} catch (IOException e) {


log.info("problem closing wal provider");
}
}
}

};