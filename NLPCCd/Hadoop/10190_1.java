//,temp,sample_2017.java,2,18,temp,sample_2023.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (cachedHomeDirectory == null) {
final HttpOpParam.Op op = GetOpParam.Op.GETHOMEDIRECTORY;
try {
String pathFromDelegatedFS = new FsPathResponseRunner<String>(op, null, new UserParam(ugi)) {
String decodeResponse(Map<?, ?> json) throws IOException {
return JsonUtilClient.getPath(json);
}
}   .run();
cachedHomeDirectory = new Path(pathFromDelegatedFS).makeQualified( this.getUri(), null);
} catch (IOException e) {


log.info("unable to get homedirectory from original file system");
}
}
}

};