//,temp,sample_4227.java,2,17,temp,sample_4226.java,2,17
//,3
public class xxx {
public void dummy_method(){
templateVars.put("buildUrlLog", YETUS_LOG_FILE);
templateVars.put("buildUrlOutputDir", YETUS_OUTPUT_FOLDER);
templateVars.put("logFile", mLogFile.getAbsolutePath());
try {
Templates.writeTemplateResult(YETUS_EXEC_VM, yetusExecScript, templateVars);
Process proc = new ProcessBuilder().command("bash", yetusExecScript.getPath()).start();
int exitCode = proc.waitFor();
if (exitCode == 0) {
}
} catch (Exception e) {


log.info("error processing yetus check");
}
}

};