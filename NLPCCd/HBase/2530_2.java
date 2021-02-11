//,temp,sample_1048.java,2,16,temp,sample_1039.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertFalse(r.isStale());
Assert.assertFalse(r.getColumnCells(f, b1).isEmpty());
SlowMeCopro.sleepTime.set(0);
SlowMeCopro.getPrimaryCdl().set(new CountDownLatch(1));
g = new Get(b1);
g.setConsistency(Consistency.TIMELINE);
r = table.get(g);
Assert.assertTrue(r.isStale());
Assert.assertTrue(r.getColumnCells(f, b1).isEmpty());
SlowMeCopro.getPrimaryCdl().get().countDown();


log.info("stale done");
}

};