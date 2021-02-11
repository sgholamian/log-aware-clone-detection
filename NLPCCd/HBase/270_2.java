//,temp,sample_3201.java,2,9,temp,sample_4804.java,2,9
//,3
public class xxx {
public void nodeDeleted(final String path) {
if(path.startsWith(watcher.znodePaths.drainingZNode)) {
final ServerName sn = ServerName.valueOf(ZKUtil.getNodeName(path));


log.info("draining rs node deleted removing from list");
}
}

};