//,temp,sample_269.java,2,16,temp,sample_268.java,2,16
//,2
public class xxx {
public void dummy_method(){
Kryo kryo = SerializationUtilities.borrowKryo();
try {
kryo.writeObject(output, partition.hashMap);
output.close();
outputStream.close();
} finally {
SerializationUtilities.releaseKryo(kryo);
}
partition.hashMapLocalPath = file.toPath();
partition.hashMapOnDisk = true;


log.info("spilling hash partition rows mem size");
}

};