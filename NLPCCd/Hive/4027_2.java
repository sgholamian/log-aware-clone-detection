//,temp,sample_4805.java,2,11,temp,sample_4806.java,2,14
//,3
public class xxx {
public void readFields(DataInput dataInput) throws IOException {
int len;
byte[] buf;
locations = new ArrayList<String>();
length = dataInput.readLong();
int numElements = dataInput.readInt();
for (int i = 0; i < numElements; i++) {
len = dataInput.readInt();


log.info("read file length of");
}
}

};