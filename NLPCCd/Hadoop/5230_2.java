//,temp,sample_6217.java,2,16,temp,sample_6216.java,2,16
//,3
public class xxx {
public void dummy_method(){
DFSTestUtil.formatNameNode(conf);
NameNode namenode = new NameNode(conf);
namenode.getNamesystem().mkdirs("/test", new PermissionStatus("hairong", null, FsPermission.getDefault()), true);
NamenodeProtocols nnRpc = namenode.getRpcServer();
assertTrue(nnRpc.getFileInfo("/test").isDir());
nnRpc.setSafeMode(SafeModeAction.SAFEMODE_ENTER, false);
nnRpc.saveNamespace();
namenode.stop();
namenode.join();
namenode.joinHttpServer();


log.info("read an uncomressed image and store it compressed using default codec");
}

};