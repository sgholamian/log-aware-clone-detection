//,temp,sample_5722.java,2,13,temp,sample_2030.java,2,17
//,3
public class xxx {
public void dummy_method(){
Admin admin = util.getAdmin();
boolean major = RandomUtils.nextInt(0, 100) < majorRatio;
LOG.info("Performing action: Compact table " + tableName + ", major=" + major);
try {
if (major) {
admin.majorCompact(tableName);
} else {
admin.compact(tableName);
}
} catch (Exception ex) {


log.info("compaction failed might be caused by other chaos");
}
}

};