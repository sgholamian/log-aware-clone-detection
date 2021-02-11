//,temp,sample_1353.java,2,12,temp,sample_3016.java,2,19
//,3
public class xxx {
public void dummy_method(){
OutputStream out = null;
try {
out = new BufferedOutputStream(new FileOutputStream(outfile));
out.write(decoded);
} catch (IOException e) {
} finally {
if (out != null) {
try {
out.close();
} catch (Exception e) {


log.info("error closing");
}
}
}
}

};