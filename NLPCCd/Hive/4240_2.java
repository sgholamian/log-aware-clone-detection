//,temp,sample_3019.java,2,13,temp,sample_2146.java,2,12
//,3
public class xxx {
private void setupSessionIO(SessionState sessionState) {
try {
sessionState.in = null;
sessionState.out = new PrintStream(new FileOutputStream(sessionState.getTmpOutputFile()), true, CharEncoding.UTF_8);
sessionState.err = new PrintStream(new FileOutputStream(sessionState.getTmpErrOutputFile()), true,CharEncoding.UTF_8);
} catch (IOException e) {


log.info("error in creating temp output file");
}
}

};