//,temp,sample_6217.java,2,16,temp,sample_6216.java,2,16
//,3
public class xxx {
public void dummy_method(){
namenode.getNamesystem().mkdirs("/test", new PermissionStatus("hairong", null, FsPermission.getDefault()), true);
NamenodeProtocols nnRpc = namenode.getRpcServer();
assertTrue(nnRpc.getFileInfo("/test").isDir());
nnRpc.setSafeMode(SafeModeAction.SAFEMODE_ENTER, false);
nnRpc.saveNamespace();
namenode.stop();
namenode.join();
namenode.joinHttpServer();
conf.setBoolean(DFSConfigKeys.DFS_IMAGE_COMPRESS_KEY, true);
checkNameSpace(conf);


log.info("read a compressed image and store it using a different codec");
}

};