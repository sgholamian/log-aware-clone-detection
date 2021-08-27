//,temp,sample_4216.java,2,18,temp,sample_4215.java,2,15
//,3
public class xxx {
private void executeCMD(String[] args, String input, int retCode) {
InputStream inputStream = null;
int ret = 0;
try {
if (input != null) {
inputStream = IOUtils.toInputStream(input);
}
ret = cli.runWithArgs(args, inputStream);
} catch (Throwable e) {


log.info("failed to execute command due to the error");
}
}

};