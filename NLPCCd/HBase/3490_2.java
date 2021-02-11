//,temp,sample_2235.java,2,8,temp,sample_1602.java,2,8
//,2
public class xxx {
protected void initializeTable(Connection connection, TableName tableName) throws IOException {
if (this.table != null || this.connection != null) {


log.info("initializetable called multiple times overwriting connection and table reference tableinputformatbase will not close these old references when done");
}
}

};