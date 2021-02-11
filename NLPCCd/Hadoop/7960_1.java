//,temp,sample_751.java,2,16,temp,sample_752.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifyEquals(locations, locationArray2);
URL url3 = new URL("http", addr.getHostString(), addr.getPort(), WebHdfsFileSystem.PATH_PREFIX + "/foo?op=GETFILEBLOCKLOCATIONS" + "&length=" + LENGTH);
String response3 = getResponse(url3, "GET");
BlockLocation[] locationArray3 = toBlockLocationArray(response3);
verifyEquals(locations, locationArray3);
URL url4 = new URL("http", addr.getHostString(), addr.getPort(), WebHdfsFileSystem.PATH_PREFIX + "/foo?op=GETFILEBLOCKLOCATIONS" + "&offset=" + OFFSET);
String response4 = getResponse(url4, "GET");
BlockLocation[] locationArray4 = toBlockLocationArray(response4);
verifyEquals(locations, locationArray4);
URL url5 = new URL("http", addr.getHostString(), addr.getPort(), WebHdfsFileSystem.PATH_PREFIX + "/foo?op=GETFILEBLOCKLOCATIONS" + "&offset=1200");


log.info("sending getfileblocklocations request");
}

};