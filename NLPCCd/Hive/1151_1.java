//,temp,sample_753.java,2,12,temp,sample_520.java,2,12
//,3
public class xxx {
private void closeAll() {
for (Closeable eval : udfs) {
try {
eval.close();
} catch (IOException e) {


log.info("hit error while closing udf");
}
}
}

};