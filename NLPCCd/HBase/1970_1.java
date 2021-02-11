//,temp,sample_1892.java,2,16,temp,sample_231.java,2,16
//,2
public class xxx {
public void dummy_method(){
if (e instanceof RetriesExhaustedWithDetailsException) {
RetriesExhaustedWithDetailsException aggEx = (RetriesExhaustedWithDetailsException)e;
exceptionInfo = aggEx.getExhaustiveDescription();
} else {
StringWriter stackWriter = new StringWriter();
PrintWriter pw = new PrintWriter(stackWriter);
e.printStackTrace(pw);
pw.flush();
exceptionInfo = StringUtils.stringifyException(e);
}


log.info("failed to insert after ms region information errors");
}

};