//,temp,sample_895.java,2,18,temp,sample_903.java,2,18
//,3
public class xxx {
public void dummy_method(){
FileSystem dfs2 = DFSTestUtil.getFileSystemAs(ugi, conf);
boolean done = false;
for(int i = 0; i < 10 && !done; i++) {
AppendTestUtil.LOG.info("i=" + i);
try {
dfs2.create(filepath, false, BUF_SIZE, REPLICATION_NUM, BLOCK_SIZE);
fail("Creation of an existing file should never succeed.");
} catch (FileAlreadyExistsException ex) {
done = true;
} catch (AlreadyBeingCreatedException ex) {


log.info("good got");
}
}
}

};