//,temp,sample_3019.java,2,13,temp,sample_2146.java,2,12
//,3
public class xxx {
private void setupSessionIO(SessionState sessionState) {
try {
sessionState.in = null;
sessionState.out = new PrintStream(System.out, true, CharEncoding.UTF_8);
sessionState.info = new PrintStream(System.err, true, CharEncoding.UTF_8);
sessionState.err = new PrintStream(System.err, true, CharEncoding.UTF_8);
} catch (UnsupportedEncodingException e) {


log.info("error creating printstream");
}
}

};