//,temp,sample_5492.java,2,16,temp,sample_5487.java,2,16
//,3
public class xxx {
public void dummy_method(){
for (HStore s : secondaryRegion.getStores()) {
assertEquals(expectedStoreFileCount, s.getStorefilesCount());
}
HStore store = secondaryRegion.getStore(Bytes.toBytes("cf1"));
long newFlushableSize = store.getFlushableSize().getHeapSize();
assertTrue(newFlushableSize > 0);
long newRegionMemstoreSize = secondaryRegion.getMemStoreSize();
assertTrue(newRegionMemstoreSize > 0);
assertTrue(regionMemstoreSize > newRegionMemstoreSize);
assertNull(secondaryRegion.getPrepareFlushResult());


log.info("verifying edits from secondary");
}

};