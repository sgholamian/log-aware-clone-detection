//,temp,sample_5036.java,2,13,temp,sample_5037.java,2,13
//,3
public class xxx {
public HCatPartition deserializePartition(String hcatPartitionStringRep) throws HCatException {
try {
Partition partition = new Partition();
new TDeserializer(new TJSONProtocol.Factory()).deserialize(partition, hcatPartitionStringRep, "UTF-8");
return new HCatPartition(null, partition);
}
catch(TException exception) {


log.info("could not de serialize partition from");
}
}

};