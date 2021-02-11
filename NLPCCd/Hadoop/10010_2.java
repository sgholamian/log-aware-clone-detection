//,temp,sample_6216.java,2,16,temp,sample_6218.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertTrue(nnRpc.getFileInfo("/test").isDir());
nnRpc.setSafeMode(SafeModeAction.SAFEMODE_ENTER, false);
nnRpc.saveNamespace();
namenode.stop();
namenode.join();
namenode.joinHttpServer();
conf.setBoolean(DFSConfigKeys.DFS_IMAGE_COMPRESS_KEY, true);
checkNameSpace(conf);
conf.set(DFSConfigKeys.DFS_IMAGE_COMPRESSION_CODEC_KEY, "org.apache.hadoop.io.compress.GzipCodec");
checkNameSpace(conf);


log.info("read a compressed image and store it as uncompressed");
}

};