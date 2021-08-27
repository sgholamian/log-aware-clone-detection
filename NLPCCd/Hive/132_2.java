//,temp,sample_3911.java,2,17,temp,sample_3909.java,2,17
//,2
public class xxx {
public void dummy_method(){
String thpFileStr = PrivilegedFileReader.read(thpFileName);
if (thpFileStr == null) {
thpFileName = "/sys/kernel/mm/redhat_transparent_hugepage/enabled";
thpFileStr = PrivilegedFileReader.read(thpFileName);
}
if (thpFileStr != null) {
int strIdx = thpFileStr.indexOf('[');
int endIdx = thpFileStr.indexOf(']');
jg.writeObjectField(thpFileName, thpFileStr.substring(strIdx + 1, endIdx));
} else {


log.info("unable to read contents of");
}
}

};