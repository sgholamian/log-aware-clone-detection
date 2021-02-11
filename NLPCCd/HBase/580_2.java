//,temp,sample_1047.java,2,16,temp,sample_1038.java,2,16
//,3
public class xxx {
public void dummy_method(){
Get g = new Get(b1);
Result r = table.get(g);
Assert.assertFalse(r.isStale());
Assert.assertFalse(r.getColumnCells(f, b1).isEmpty());
SlowMeCopro.sleepTime.set(2000);
g = new Get(b1);
r = table.get(g);
Assert.assertFalse(r.isStale());
Assert.assertFalse(r.getColumnCells(f, b1).isEmpty());
SlowMeCopro.sleepTime.set(0);


log.info("sleep and is not stale done");
}

};