//,temp,sample_5509.java,2,18,temp,sample_5514.java,2,15
//,3
public class xxx {
public void cleanup(Context context) throws IOException, InterruptedException {
for (RecordFactory factory : reduces) {
key.setSeed(r.nextLong());
while (factory.next(key, val)) {
context.progress();
context.write(key, val);
key.setSeed(r.nextLong());
try {
matcher.match();
} catch (Exception e) {


log.info("error in resource usage emulation message");
}
}
}
}

};