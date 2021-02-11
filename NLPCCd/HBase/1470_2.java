//,temp,sample_1411.java,2,15,temp,sample_3855.java,2,16
//,3
public class xxx {
private static void deleteTable(Configuration conf, String[] args) {
TableName tableName = TableName.valueOf(args[0]);
try (Connection connection = ConnectionFactory.createConnection(conf);
Admin admin = connection.getAdmin()) {
try {
admin.disableTable(tableName);
} catch (TableNotEnabledException e) {
}
admin.deleteTable(tableName);
} catch (IOException e) {


log.info("dry run failed to delete table s n s");
}
}

};