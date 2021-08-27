//,temp,sample_2848.java,2,16,temp,sample_2849.java,2,16
//,3
public class xxx {
public boolean onEviction(String key, ReplyHandler value) {
try {
if (listener != null) {
listener.onEviction(key);
}
} catch (Throwable e) {
}
try {
value.onTimeout(key);
} catch (Throwable e) {


log.info("error processing ontimeout for correlationid due this exception is ignored");
}
}

};