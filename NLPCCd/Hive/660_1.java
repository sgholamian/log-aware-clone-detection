//,temp,sample_1080.java,6,17,temp,sample_1076.java,2,11
//,3
public class xxx {
public void close() throws IOException {
if (orcWriter != null) {
try {
orcWriter.close();
orcWriter = null;
} catch (Exception ex) {


log.info("failed to close orc writer");
}
}
}

};