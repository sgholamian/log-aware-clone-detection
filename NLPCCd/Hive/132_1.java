//,temp,sample_3911.java,2,17,temp,sample_3909.java,2,17
//,2
public class xxx {
public void dummy_method(){
String thpDefragFileStr = PrivilegedFileReader.read(thpDefragFileName);
if (thpDefragFileStr == null) {
thpDefragFileName = "/sys/kernel/mm/redhat_transparent_hugepage/defrag";
thpDefragFileStr = PrivilegedFileReader.read(thpDefragFileName);
}
if (thpDefragFileStr != null) {
int strIdx = thpDefragFileStr.indexOf('[');
int endIdx = thpDefragFileStr.indexOf(']');
jg.writeObjectField(thpDefragFileName, thpDefragFileStr.substring(strIdx + 1, endIdx));
} else {


log.info("unable to read contents of");
}
}

};