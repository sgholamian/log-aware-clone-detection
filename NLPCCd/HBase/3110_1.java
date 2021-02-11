//,temp,sample_458.java,2,12,temp,sample_3895.java,2,12
//,3
public class xxx {
protected void deleteTable() throws Exception {
if (util.getAdmin().tableExists(TABLE_NAME)) {
if (!util.getAdmin().isTableDisabled(TABLE_NAME)) {
util.getAdmin().disableTable(TABLE_NAME);
}
util.getAdmin().deleteTable(TABLE_NAME);


log.info("deleted table");
}
}

};