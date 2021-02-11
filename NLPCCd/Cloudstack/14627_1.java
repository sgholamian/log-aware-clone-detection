//,temp,sample_5218.java,2,17,temp,sample_1176.java,2,17
//,2
public class xxx {
public void dummy_method(){
final String privateIp = cmd.getIp();
final int cmdPort = cmd.getPort();
if (s_logger.isDebugEnabled()) {
}
try {
final String result = connect(cmd.getName(), privateIp, cmdPort);
if (result != null) {
return new CheckSshAnswer(cmd, "Can not ping System vm " + vmName + "due to:" + result);
}
} catch (final Exception e) {


log.info("can not ping system vm due to exception");
}
}

};