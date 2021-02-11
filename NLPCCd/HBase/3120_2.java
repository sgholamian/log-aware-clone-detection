//,temp,sample_1411.java,2,15,temp,sample_3856.java,2,16
//,3
public class xxx {
public void dummy_method(){
try (Connection connection = ConnectionFactory.createConnection(conf);
Admin admin = connection.getAdmin()) {
try {
admin.disableTable(tableName);
} catch (TableNotEnabledException e) {
}
admin.deleteTable(tableName);
} catch (IOException e) {
return;
}


log.info("dry run deleted table s");
}

};