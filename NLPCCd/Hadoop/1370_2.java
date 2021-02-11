//,temp,sample_8884.java,2,12,temp,sample_5439.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
zkDoWithRetries(new ZKAction<Void>() {
public Void run() throws KeeperException, InterruptedException {
ZKUtil.deleteRecursive(zkClient, znodeWorkingDir);
return null;
}
});
} catch (KeeperException e) {
throw new IOException("Couldn't clear parent znode " + znodeWorkingDir, e);
}


log.info("successfully deleted from zk");
}

};