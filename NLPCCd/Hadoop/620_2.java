//,temp,sample_8657.java,2,7,temp,sample_3532.java,2,11
//,3
public class xxx {
public static void setSessionTimeZone(Configuration conf, Connection conn) throws SQLException {
Method method;
try {
method = conn.getClass().getMethod( "setSessionTimeZone", new Class [] {String.class});
} catch (Exception ex) {


log.info("could not find method setsessiontimezone in");
}
}

};