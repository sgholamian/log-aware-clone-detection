//,temp,sample_1093.java,2,16,temp,sample_1092.java,2,16
//,3
public class xxx {
public void dummy_method(){
mr.isEmpty = true;
StripeInformation si = Mockito.mock(StripeInformation.class);
Mockito.when(si.getNumberOfRows()).thenReturn(0L);
osm = new OrcStripeMetadata(stripeKey, mr, si, null, null, null, null);
mr.doStreamStep = true;
osm = new OrcStripeMetadata(stripeKey, mr, si, null, null, null, null);
mr.isEmpty = false;
stripeKey = new OrcBatchKey(0, 0, 0);
osm = new OrcStripeMetadata(stripeKey, mr, si, null, null, null, null);
osm.resetRowIndex();


log.info("estimated for a test osm w o row index");
}

};