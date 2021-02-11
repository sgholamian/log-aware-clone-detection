//,temp,sample_1353.java,2,12,temp,sample_3016.java,2,19
//,3
public class xxx {
private void writeStringToFile(String s, String fileName) {
try {
BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
out.write(s);
out.close();
} catch (IOException e) {


log.info("error writing to");
}
}

};