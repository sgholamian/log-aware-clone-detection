//,temp,sample_6838.java,2,9,temp,sample_6771.java,2,11
//,3
public class xxx {
protected InputStream resolveResource(String resource) throws IOException {
InputStream is = null;
try {
is = new FileInputStream(resource);
} catch (FileNotFoundException e) {


log.info("could not open resource as a file");
}
}

};