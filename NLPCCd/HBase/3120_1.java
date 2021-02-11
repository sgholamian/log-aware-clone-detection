//,temp,sample_1411.java,2,15,temp,sample_3856.java,2,16
//,3
public class xxx {
public void tearDownTestCoprocessorWhitelistMasterObserver() throws Exception {
Admin admin = UTIL.getAdmin();
try {
try {
admin.disableTable(TEST_TABLE);
} catch (TableNotEnabledException ex) {
}
admin.deleteTable(TEST_TABLE);
} catch (TableNotFoundException ex) {


log.info("table was not created for some reason");
}
}

};