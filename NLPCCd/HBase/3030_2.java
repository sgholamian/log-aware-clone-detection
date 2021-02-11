//,temp,sample_5322.java,2,13,temp,sample_5320.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<String> childrenPaths = null;
long sleepTime = 1000;
while (!shouldStop) {
try {
childrenPaths = ZKUtil.listChildrenAndWatchForNewChildren(watcher, watcher.znodePaths.splitLogZNode);
if (childrenPaths != null) {
return childrenPaths;
}
} catch (KeeperException e) {
}


log.info("retry listchildren of znode after sleep for ms");
}
}

};