//,temp,sample_4325.java,2,16,temp,sample_4324.java,2,17
//,3
public class xxx {
public void dummy_method(){
if(cliDriver == null) {
cliDriver = new CliDriver();
}
cliDriver.processLine("set test.data.dir=" + testFiles + ";");
File scriptFile = new File(this.initScript);
if (!scriptFile.isFile()) {
return;
}
conf.setBoolean("hive.test.init.phase", true);
String initCommands = readEntireFileIntoString(scriptFile);


log.info("initial setup");
}

};