//,temp,sample_1041.java,2,16,temp,sample_1040.java,2,16
//,3
public class xxx {
public void dummy_method(){
g.setConsistency(Consistency.TIMELINE);
r = table.get(g);
Assert.assertTrue(r.isStale());
Assert.assertTrue(r.getColumnCells(f, b1).isEmpty());
SlowMeCopro.getPrimaryCdl().get().countDown();
g = new Get(b1);
g.setCheckExistenceOnly(true);
r = table.get(g);
Assert.assertFalse(r.isStale());
Assert.assertTrue(r.getExists());


log.info("exists not stale done");
}

};