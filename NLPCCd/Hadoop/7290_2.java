//,temp,sample_987.java,2,11,temp,sample_5629.java,2,17
//,3
public class xxx {
public void dummy_method(){
new FsActionParam("r-x");
new FsActionParam("-wx");
new FsActionParam("r--");
new FsActionParam("-w-");
new FsActionParam("--x");
new FsActionParam("---");
try {
new FsActionParam("rw");
Assert.fail();
} catch(IllegalArgumentException e) {


log.info("expected");
}
}

};