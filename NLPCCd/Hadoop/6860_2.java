//,temp,sample_370.java,2,17,temp,sample_5950.java,2,13
//,3
public class xxx {
public void closeWriter(JobId id) throws IOException {
try {
final MetaInfo mi = fileMap.get(id);
if (mi != null) {
mi.closeWriter();
}
} catch (IOException e) {


log.info("error closing writer for jobid");
}
}

};