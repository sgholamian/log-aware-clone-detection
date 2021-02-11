//,temp,sample_4304.java,2,16,temp,sample_4303.java,2,16
//,3
public class xxx {
public void dummy_method(){
result = command.execute();
if (result != null) {
String errMsg = "Unable to mount " + remoteDevice + " at " + localRootPath + " due to " + result;
s_logger.error(errMsg);
File file = new File(localRootPath);
if (file.exists()) {
file.delete();
}
throw new CloudRuntimeException(errMsg);
}


log.info("successfully mounted at");
}

};