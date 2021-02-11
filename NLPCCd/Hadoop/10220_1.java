//,temp,sample_895.java,2,18,temp,sample_903.java,2,18
//,3
public class xxx {
public void dummy_method(){
for(int i = 0; i < 10; i++) {
AppendTestUtil.LOG.info("i=" + i);
try {
dfs2.create(filepath, false, BUF_SIZE, (short)1, BLOCK_SIZE);
fail("Creation of an existing file should never succeed.");
} catch(FileAlreadyExistsException e) {
return;
} catch(AlreadyBeingCreatedException e) {
return;
} catch(IOException ioe) {


log.info("unexpected");
}
}
}

};