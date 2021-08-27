//,temp,HybridHashTableContainer.java,1263,1299,temp,MapJoinBytesTableContainer.java,421,445
//,3
public class xxx {
  @Override
  public void setSerde(MapJoinObjectSerDeContext keyCtx, MapJoinObjectSerDeContext valCtx)
      throws SerDeException {

    // Save the key and value contexts in case the MapJoinOperator needs them when creating
    // a stand alone spill MapJoinBytesTableContainer.
    keyContext = keyCtx;
    valueContext = valCtx;

    // Save the key serde for possible use by NonMatchedSmallTableIteratorImpl.
    keySerde = keyCtx.getSerDe();

    AbstractSerDe valSerde = valCtx.getSerDe();

    if (writeHelper == null) {
      LOG.info("Initializing container with " + keySerde.getClass().getName() + " and "
          + valSerde.getClass().getName());

      // We assume this hashtable is loaded only when tez is enabled
      LazyBinaryStructObjectInspector valSoi =
          (LazyBinaryStructObjectInspector) valSerde.getObjectInspector();
      writeHelper = new MapJoinBytesTableContainer.LazyBinaryKvWriter(keySerde, valSoi,
          valCtx.hasFilterTag());
      if (internalValueOi == null) {
        internalValueOi = valSoi;
      }
      if (sortableSortOrders == null) {
        sortableSortOrders = ((BinarySortableSerDe) keySerde).getSortOrders();
      }
      if (nullMarkers == null) {
        nullMarkers = ((BinarySortableSerDe) keySerde).getNullMarkers();
      }
      if (notNullMarkers == null) {
        notNullMarkers = ((BinarySortableSerDe) keySerde).getNotNullMarkers();
      }
    }
  }

};