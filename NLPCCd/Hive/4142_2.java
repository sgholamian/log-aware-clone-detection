//,temp,sample_5102.java,2,14,temp,sample_1039.java,2,12
//,3
public class xxx {
public List<Path> getAliasBucketFiles(String refTableInputFile, String refTableAlias, String alias) {
List<String> pathStr=aliasBucketMapping.get(alias).get(refTableInputFile);
List<Path> paths = new ArrayList<Path>();
if(pathStr!=null) {
for (String p : pathStr) {


log.info("loading file for");
}
}
}

};