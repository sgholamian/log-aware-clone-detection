//,temp,sample_4325.java,2,16,temp,sample_4324.java,2,17
//,3
public class xxx {
public void dummy_method(){
boolean canReuseSession = (tname == null) || !qNoSessionReuseQuerySet.contains(tname);
if(!isSessionStateStarted) {
startSessionState(canReuseSession);
}
if(cliDriver == null) {
cliDriver = new CliDriver();
}
cliDriver.processLine("set test.data.dir=" + testFiles + ";");
File scriptFile = new File(this.initScript);
if (!scriptFile.isFile()) {


log.info("no init script detected skipping");
}
}

};