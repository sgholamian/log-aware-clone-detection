//,temp,sample_4190.java,2,12,temp,sample_1608.java,2,12
//,3
public class xxx {
protected void closeTable() {
for (Table table : userVsTable.values()) {
try {
table.close();
} catch (Exception e) {


log.info("error while closing the table");
}
}
}

};