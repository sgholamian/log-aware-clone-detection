//,temp,sample_1047.java,2,16,temp,sample_1038.java,2,16
//,3
public class xxx {
public void dummy_method(){
SlowMeCopro.getSecondaryCdl().set(new CountDownLatch(1));
g = new Get(b1);
g.setConsistency(Consistency.TIMELINE);
r = table.get(g);
Assert.assertFalse(r.isStale());
Assert.assertFalse(r.getColumnCells(f, b1).isEmpty());
Assert.assertEquals(hedgedReadOps.getCount(), 1);
Assert.assertEquals(hedgedReadWin.getCount(), 0);
SlowMeCopro.sleepTime.set(0);
SlowMeCopro.getSecondaryCdl().get().countDown();


log.info("hedged read occurred but not faster");
}

};