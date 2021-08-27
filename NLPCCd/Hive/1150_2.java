//,temp,sample_4038.java,2,10,temp,sample_3109.java,2,18
//,3
public class xxx {
public void dummy_method(){
Throwable cause = e.getCause();
if (cause instanceof TooLongFrameException) {
sendError(ctx, BAD_REQUEST);
return;
} else if (cause instanceof IOException) {
if (cause instanceof ClosedChannelException) {
return;
}
String message = String.valueOf(cause.getMessage());
if (IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {


log.info("ignoring client socket close");
}
}
}

};