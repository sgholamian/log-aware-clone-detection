//,temp,sample_5096.java,2,10,temp,sample_465.java,2,9
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