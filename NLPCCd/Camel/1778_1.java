//,temp,sample_861.java,2,8,temp,sample_860.java,2,8
//,2
public class xxx {
public void changeFileName(String newName) {
String newFileName = FileUtil.normalizePath(newName);
String newEndpointPath = FileUtil.normalizePath(endpointPath.endsWith("" + File.separatorChar) ? endpointPath : endpointPath + File.separatorChar);


log.info("normalized newfilename");
}

};