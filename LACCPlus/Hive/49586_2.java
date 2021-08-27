//,temp,HybridHashTableContainer.java,1263,1299,temp,MapJoinBytesTableContainer.java,421,445
//,3
public class xxx {
  @Override
  public void setSerde(MapJoinObjectSerDeContext keyContext, MapJoinObjectSerDeContext valueContext)
      throws SerDeException {
    keySerde = keyContext.getSerDe();
    AbstractSerDe valSerde = valueContext.getSerDe();
    if (writeHelper == null) {
      LOG.info("Initializing container with " + keySerde.getClass().getName() + " and "
          + valSerde.getClass().getName());
      if (keySerde instanceof BinarySortableSerDe && valSerde instanceof LazyBinarySerDe) {
        LazyBinaryStructObjectInspector valSoi =
            (LazyBinaryStructObjectInspector) valSerde.getObjectInspector();
        writeHelper = new LazyBinaryKvWriter(keySerde, valSoi, valueContext.hasFilterTag());
        internalValueOi = valSoi;
        sortableSortOrders = ((BinarySortableSerDe) keySerde).getSortOrders();
        nullMarkers = ((BinarySortableSerDe) keySerde).getNullMarkers();
        notNullMarkers = ((BinarySortableSerDe) keySerde).getNotNullMarkers();
      } else {
        writeHelper = new KeyValueWriter(keySerde, valSerde, valueContext.hasFilterTag());
        internalValueOi = createInternalOi(valueContext);
        sortableSortOrders = null;
        nullMarkers = null;
        notNullMarkers = null;
      }
    }
  }

};