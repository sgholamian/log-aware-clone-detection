//,temp,sample_99.java,2,16,temp,sample_98.java,2,11
//,3
public class xxx {
public void tearDown() throws IOException {
IOException ex = null;
try {
region.close();
} catch (IOException e) {
ex = e;
}
try {
hlog.close();
} catch (IOException e) {


log.info("caught exception");
}
}

};