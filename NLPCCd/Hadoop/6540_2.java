//,temp,sample_4708.java,2,13,temp,sample_4897.java,2,12
//,3
public class xxx {
public void close() throws IOException {
for (KMSClientProvider provider : providers) {
try {
provider.close();
} catch (IOException ioe) {


log.info("error closing provider with url");
}
}
}

};