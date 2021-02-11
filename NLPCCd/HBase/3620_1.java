//,temp,sample_742.java,2,16,temp,sample_743.java,2,16
//,2
public class xxx {
public void dummy_method(){
HRegionInfo parent = new HRegionInfo(td.getTableName(), Bytes.toBytes("aaa"), Bytes.toBytes("eee"));
HRegionInfo splita = new HRegionInfo(td.getTableName(), Bytes.toBytes("aaa"), Bytes.toBytes("ccc"));
HRegionInfo splitb = new HRegionInfo(td.getTableName(), Bytes.toBytes("ccc"), Bytes.toBytes("eee"));
Result parentMetaRow = createResult(parent, splita, splitb);
FileSystem fs = FileSystem.get(HTU.getConfiguration());
Path rootdir = this.masterServices.getMasterFileSystem().getRootDir();
FSUtils.setRootDir(fs.getConf(), rootdir);
Path tabledir = FSUtils.getTableDir(rootdir, td.getTableName());
Path storedir = HStore.getStoreHomedir(tabledir, parent, td.getColumnFamilies()[0].getName());
Path storeArchive = HFileArchiveUtil.getStoreArchivePath(this.masterServices.getConfiguration(), parent, tabledir, td.getColumnFamilies()[0].getName());


log.info("table dir");
}

};