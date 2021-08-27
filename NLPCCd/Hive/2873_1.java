//,temp,sample_1090.java,2,16,temp,sample_1089.java,2,16
//,3
public class xxx {
public void dummy_method(){
ObjectEstimator root = map.get(OrcStripeMetadata.class);
OrcBatchKey stripeKey = null;
DummyMetadataReader mr = new DummyMetadataReader();
mr.doStreamStep = false;
mr.isEmpty = true;
StripeInformation si = Mockito.mock(StripeInformation.class);
Mockito.when(si.getNumberOfRows()).thenReturn(0L);
osm = new OrcStripeMetadata(stripeKey, mr, si, null, null, null, null);
mr.doStreamStep = true;
osm = new OrcStripeMetadata(stripeKey, mr, si, null, null, null, null);


log.info("estimated for an empty osm after serde");
}

};