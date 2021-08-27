//,temp,sample_5432.java,2,19,temp,sample_5431.java,2,17
//,3
public class xxx {
public void addTable(Text tableName) throws AccumuloException, AccumuloSecurityException {
if(this.simulate) {
} else {
BatchWriter bw = null;
String table = tableName.toString();
if(this.createTables && !this.conn.tableOperations().exists(table)) {
try {
this.conn.tableOperations().create(table);
} catch (AccumuloSecurityException var8) {


log.info("accumulo security violation creating");
}
}
}
}

};