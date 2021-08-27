//,temp,sample_731.java,2,10,temp,sample_3358.java,2,10
//,2
public class xxx {
protected Context createJndiContext() throws Exception {
Properties properties = new Properties();
InputStream in = getClass().getClassLoader().getResourceAsStream("jndi.properties");
if (in != null) {


log.info("using jndi properties from classpath root");
}
}

};