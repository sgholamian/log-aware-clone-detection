//,temp,sample_4958.java,2,16,temp,sample_4961.java,2,16
//,2
public class xxx {
public void dummy_method(){
Properties serverInfo = new Properties();
try {
InputStream is = getResource(name + ".properties");
serverInfo.load(is);
is.close();
} catch (IOException ex) {
throw new RuntimeException("Could not load server information file: " + name + ".properties");
}
initLog();
log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++");


log.info("built by build username undef");
}

};