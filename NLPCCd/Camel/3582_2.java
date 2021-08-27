//,temp,sample_5708.java,2,9,temp,sample_5709.java,2,11
//,3
public class xxx {
public static void putConfig(String confName, SolrZkClient zkClient, File solrhome, final String srcName, String destName) throws Exception {
File file = new File(solrhome, "collection1" + File.separator + "conf" + File.separator + srcName);
if (!file.exists()) {
return;
}
String destPath = "/configs/" + confName + "/" + destName;


log.info("zk put to");
}

};