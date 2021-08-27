//,temp,sample_780.java,2,18,temp,sample_781.java,2,18
//,3
public class xxx {
public void dummy_method(){
Schema fileSchema = recordWritable.getFileSchema();
UID recordReaderId = recordWritable.getRecordReaderID();
if(!noEncodingNeeded.contains(recordReaderId)) {
SchemaReEncoder reEncoder = null;
if(reEncoderCache.containsKey(recordReaderId)) {
reEncoder = reEncoderCache.get(recordReaderId);
} else if (!r.getSchema().equals(readerSchema)) {
reEncoder = new SchemaReEncoder(r.getSchema(), readerSchema);
reEncoderCache.put(recordReaderId, reEncoder);
} else{


log.info("adding new valid rrid");
}
}
}

};