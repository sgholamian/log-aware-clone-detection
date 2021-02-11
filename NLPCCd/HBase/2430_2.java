//,temp,sample_99.java,2,16,temp,sample_2999.java,2,19
//,3
public class xxx {
public void dummy_method(){
try {
ois = new ObjectInputStream(new ByteArrayInputStream(objBytes));
obj = ois.readObject();
} catch (IOException e) {
} catch (ClassNotFoundException e) {
} finally {
if (ois != null) {
try {
ois.close();
} catch (Exception e) {


log.info("error closing objectinputstream");
}
}
}
}

};