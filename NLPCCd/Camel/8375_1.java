//,temp,sample_1043.java,2,16,temp,sample_5103.java,2,16
//,2
public class xxx {
public void dummy_method(){
if (!fileStore.exists()) {
File parent = fileStore.getParentFile();
if (parent != null) {
parent.mkdirs();
}
boolean created = FileUtil.createNewFile(fileStore);
if (!created) {
throw new IOException("Cannot create filestore: " + fileStore);
}
}


log.info("loading to level cache from state filestore");
}

};