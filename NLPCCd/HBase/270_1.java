//,temp,sample_3201.java,2,9,temp,sample_4804.java,2,9
//,3
public class xxx {
public void nodeDeleted(String path) {
if (path.startsWith(watcher.znodePaths.rsZNode)) {
String serverName = ZKUtil.getNodeName(path);


log.info("regionserver ephemeral node deleted processing expiration");
}
}

};