//,temp,sample_1551.java,2,12,temp,sample_1550.java,2,12
//,2
public class xxx {
public final void testInitialize() {
StartupCommand[] startCmds = s_hypervresource.initialize();
Command[] cmds = new Command[] {startCmds[0], startCmds[1]};
String result = s_gson.toJson(cmds);
if (result == null) {
result = "NULL";
}


log.info("testinitialize returned");
}

};