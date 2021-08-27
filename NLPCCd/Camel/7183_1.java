//,temp,sample_1452.java,2,14,temp,sample_1451.java,2,11
//,3
public class xxx {
public void setUp() throws Exception {
super.setUp();
File directory = new File(PATH);
if (!directory.isDirectory() || !directory.exists()) {
} else {
File[] files = directory.listFiles();
for (File file : files) {


log.info("deleting s");
}
}
}

};