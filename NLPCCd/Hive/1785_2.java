//,temp,sample_3421.java,2,17,temp,sample_1984.java,2,17
//,3
public class xxx {
public void dummy_method(){
key.set(pos);
pos++;
Map<String, Object> record = iterator.next();
if ((record != null) && (!record.isEmpty())) {
for (Entry<String, Object> entry : record.entrySet()) {
value.put(new Text(entry.getKey()), entry.getValue() == null ? NullWritable.get() : new ObjectWritable(entry.getValue()));
}
return true;
}
else {


log.info("jdbcrecordreader got null record");
}
}

};