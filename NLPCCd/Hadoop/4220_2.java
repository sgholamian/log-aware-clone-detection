//,temp,sample_2818.java,2,13,temp,sample_1240.java,2,12
//,3
public class xxx {
public void close() {
try {
if (writer != null) {
writer.close();
}
} catch (Exception e) {


log.info("exception closing writer");
}
}

};