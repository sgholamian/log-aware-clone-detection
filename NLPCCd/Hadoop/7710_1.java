//,temp,sample_901.java,2,16,temp,sample_902.java,2,16
//,3
public class xxx {
public void dummy_method(){
DFSTestUtil.updateConfWithFakeGroupMapping(conf, u2g_map);
cluster.setLeasePeriod(HdfsConstants.LEASE_SOFTLIMIT_PERIOD, HdfsConstants.LEASE_HARDLIMIT_PERIOD);
String filestr = "/foo" + AppendTestUtil.nextInt();
AppendTestUtil.LOG.info("filestr=" + filestr);
Path filepath = new Path(filestr);
FSDataOutputStream stm = dfs.create(filepath, true, BUF_SIZE, REPLICATION_NUM, BLOCK_SIZE);
assertTrue(dfs.dfs.exists(filestr));
int size = AppendTestUtil.nextInt(FILE_SIZE);
AppendTestUtil.LOG.info("size=" + size);
stm.write(buffer, 0, size);


log.info("hflush");
}

};