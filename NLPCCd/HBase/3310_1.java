//,temp,sample_3016.java,2,19,temp,sample_3011.java,2,18
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