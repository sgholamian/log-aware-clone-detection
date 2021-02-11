//,temp,sample_5414.java,2,14,temp,sample_5612.java,2,11
//,3
public class xxx {
private static String robustToString(Object o) {
if (o == null) {
return NULL_RESULT;
} else {
try {
return o.toString();
} catch (Exception e) {


log.info("exception calling tostring");
}
}
}

};