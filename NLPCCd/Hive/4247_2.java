//,temp,sample_780.java,2,18,temp,sample_781.java,2,18
//,3
public class xxx {
public void dummy_method(){
if(reEncoderCache.containsKey(recordReaderId)) {
reEncoder = reEncoderCache.get(recordReaderId);
} else if (!r.getSchema().equals(readerSchema)) {
reEncoder = new SchemaReEncoder(r.getSchema(), readerSchema);
reEncoderCache.put(recordReaderId, reEncoder);
} else{
noEncodingNeeded.add(recordReaderId);
}
if(reEncoder != null) {
if (!warnedOnce) {


log.info("received different schemas have to re encode size id");
}
}
}

};