//,temp,sample_2721.java,2,10,temp,sample_465.java,2,9
//,3
public class xxx {
public void tearDown() throws Exception {
try {
TEST_UTIL.deleteTable(TEST_TABLE.getTableName());
} catch (TableNotFoundException ex) {


log.info("test deleted table");
}
}

};