//,temp,sample_67.java,2,18,temp,sample_3875.java,2,12
//,3
public class xxx {
public void dummy_method(){
if (stagingDir != null) {
try {
sinkFs.delete(new Path(stagingDir), true);
} catch (IOException e) {
}
}
if (table != null) {
try {
table.close();
} catch (IOException e) {


log.info("failed to close the table");
}
}
}

};