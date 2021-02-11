//,temp,sample_4455.java,2,13,temp,sample_4454.java,2,12
//,3
public class xxx {
public static void reportLoadInfo(String gsonLoadInfo) {
if (reportMethod != null) {
try {
reportMethod.invoke(ConsoleProxy.context, gsonLoadInfo);
} catch (IllegalAccessException e) {


log.info("unable to invoke reportloadinfo due to");
}
}
}

};