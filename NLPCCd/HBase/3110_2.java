//,temp,sample_458.java,2,12,temp,sample_3895.java,2,12
//,3
public class xxx {
public void setUp() throws Exception {
UTIL.initializeCluster(REGION_SERVER_COUNT);
admin = UTIL.getAdmin();
if (admin.tableExists(TABLE_NAME)) {
if (admin.isTableEnabled(TABLE_NAME)) admin.disableTable(TABLE_NAME);
admin.deleteTable(TABLE_NAME);
}


log.info("cluster ready");
}

};