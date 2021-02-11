//,temp,sample_1545.java,2,16,temp,sample_1544.java,2,16
//,2
public class xxx {
public void dummy_method(){
defaultStartRoutCmd.setGuid("1");
defaultStartRoutCmd.setName("1");
defaultStartRoutCmd.setPrivateIpAddress("1");
defaultStartRoutCmd.setStorageIpAddress("1");
defaultStartRoutCmd.setCpus(12);
defaultStartRoutCmd.setVersion("4.2.0");
StartupCommand scmd = defaultStartRoutCmd;
Command[] cmds = {scmd};
String cmdsStr = s_gson.toJson(cmds);
Command[] result = s_gson.fromJson(cmdsStr, Command[].class);


log.info("commands first element has type");
}

};