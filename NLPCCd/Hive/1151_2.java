//,temp,sample_753.java,2,12,temp,sample_520.java,2,12
//,3
public class xxx {
public static void closeAll() {
for (String handleId : connectionMap.keySet()) {
try {
close(handleId);
} catch (Exception err) {


log.info("error closing handle id");
}
}
}

};