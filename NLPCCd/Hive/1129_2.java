//,temp,sample_4360.java,2,17,temp,sample_4359.java,2,15
//,3
public class xxx {
private void markDead(boolean success) {
if(success) {
return;
}
isClosed = true;
try {
abort(true);
}
catch(Exception ex) {


log.info("fatal error on cause");
}
}

};