//,temp,sample_1048.java,2,16,temp,sample_1039.java,2,16
//,3
public class xxx {
public void dummy_method(){
SlowMeCopro.getSecondaryCdl().get().countDown();
SlowMeCopro.getPrimaryCdl().set(new CountDownLatch(1));
g = new Get(b1);
g.setConsistency(Consistency.TIMELINE);
r = table.get(g);
Assert.assertTrue(r.isStale());
Assert.assertTrue(r.getColumnCells(f, b1).isEmpty());
Assert.assertEquals(hedgedReadOps.getCount(), 2);
Assert.assertEquals(hedgedReadWin.getCount(), 1);
SlowMeCopro.getPrimaryCdl().get().countDown();


log.info("hedged read occurred and faster");
}

};