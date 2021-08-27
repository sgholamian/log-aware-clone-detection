//,temp,sample_3201.java,2,13,temp,sample_8152.java,2,16
//,3
public class xxx {
public void create() throws Exception {
JdbcTemplate jdbc = new JdbcTemplate(dataSource);
String sql = "create table orders (\n" + "  id integer primary key,\n" + "  item varchar(10),\n" + "  amount varchar(5),\n" + "  description varchar(30),\n" + "  processed boolean\n" + ")";
try {
jdbc.execute("drop table orders");
} catch (Throwable e) {
}
jdbc.execute(sql);


log.info("created table orders");
}

};