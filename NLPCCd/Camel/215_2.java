//,temp,sample_129.java,2,13,temp,sample_1450.java,2,10
//,3
public class xxx {
public void setUp() throws Exception {
super.setUp();
File directory = new File(PATH);
if (!directory.isDirectory() || !directory.exists()) {


log.info("cannot delete files from directory s because path is not a directory or it doesn t exist");
}
}

};