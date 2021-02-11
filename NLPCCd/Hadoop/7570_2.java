//,temp,sample_4586.java,2,10,temp,sample_1665.java,2,13
//,3
public class xxx {
public void writeTransactionIdFileToStorage(long txid, NameNodeDirType type) {
for (Iterator<StorageDirectory> it = dirIterator(type); it.hasNext();) {
StorageDirectory sd = it.next();
try {
writeTransactionIdFile(sd, txid);
} catch(IOException e) {


log.info("writetransactionidtostorage failed on");
}
}
}

};