//,temp,sample_3201.java,2,13,temp,sample_8152.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (connection == null) {
EmbeddedDriver driver = new EmbeddedDriver();
connection = driver.connect(url + ";create=true", null);
}
String sql = "create table ORDERS (\n" + "  ORD_ID integer primary key,\n" + "  ITEM varchar(10),\n" + "  ITEM_COUNT varchar(5),\n" + "  ITEM_DESC varchar(30),\n" + "  ORD_DELETED boolean\n" + ")";
try {
execute("drop table orders");
} catch (Throwable e) {
}
execute(sql);


log.info("database tables created");
}

};