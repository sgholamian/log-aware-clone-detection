//,temp,sample_2017.java,2,18,temp,sample_2023.java,2,17
//,3
public class xxx {
public void dummy_method(){
storageStatistics.incrementOpCounter(OpType.GET_TRASH_ROOT);
final HttpOpParam.Op op = GetOpParam.Op.GETTRASHROOT;
try {
String strTrashPath = new FsPathResponseRunner<String>(op, path) {
String decodeResponse(Map<?, ?> json) throws IOException {
return JsonUtilClient.getPath(json);
}
}.run();
return new Path(strTrashPath).makeQualified(getUri(), null);
} catch(IOException e) {


log.info("cannot find trash root of");
}
}

};