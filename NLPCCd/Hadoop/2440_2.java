//,temp,sample_751.java,2,16,temp,sample_749.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifyEquals(locations, locationArray1);
URL url2 = new URL("http", addr.getHostString(), addr.getPort(), WebHdfsFileSystem.PATH_PREFIX + "/foo?op=GETFILEBLOCKLOCATIONS" + "&length=" + LENGTH + "&offset=" + OFFSET);
String response2 = getResponse(url2, "GET");
BlockLocation[] locationArray2 = toBlockLocationArray(response2);
verifyEquals(locations, locationArray2);
URL url3 = new URL("http", addr.getHostString(), addr.getPort(), WebHdfsFileSystem.PATH_PREFIX + "/foo?op=GETFILEBLOCKLOCATIONS" + "&length=" + LENGTH);
String response3 = getResponse(url3, "GET");
BlockLocation[] locationArray3 = toBlockLocationArray(response3);
verifyEquals(locations, locationArray3);
URL url4 = new URL("http", addr.getHostString(), addr.getPort(), WebHdfsFileSystem.PATH_PREFIX + "/foo?op=GETFILEBLOCKLOCATIONS" + "&offset=" + OFFSET);


log.info("sending getfileblocklocations request");
}

};