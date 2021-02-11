//,temp,sample_4035.java,2,10,temp,sample_980.java,2,10
//,3
public class xxx {
private static void cleanUp() throws Exception {
try {
deleteTable(TEST_UTIL, TEST_TABLE);
} catch (TableNotFoundException ex) {


log.info("test deleted table");
}
}

};