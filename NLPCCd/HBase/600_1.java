//,temp,sample_1041.java,2,16,temp,sample_1040.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertFalse(r.isStale());
Assert.assertTrue(r.getExists());
SlowMeCopro.getPrimaryCdl().set(new CountDownLatch(1));
g = new Get(b1);
g.setCheckExistenceOnly(true);
g.setConsistency(Consistency.TIMELINE);
r = table.get(g);
Assert.assertTrue(r.isStale());
Assert.assertFalse("The secondary has stale data", r.getExists());
SlowMeCopro.getPrimaryCdl().get().countDown();


log.info("exists stale before flush done");
}

};