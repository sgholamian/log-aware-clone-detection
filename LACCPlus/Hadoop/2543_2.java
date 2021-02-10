//,temp,TestCryptoCodec.java,110,229,temp,TestCodec.java,179,287
//,3
public class xxx {
  private static void codecTest(Configuration conf, int seed, int count, 
                                String codecClass) 
    throws IOException {
    
    // Create the codec
    CompressionCodec codec = null;
    try {
      codec = (CompressionCodec)
        ReflectionUtils.newInstance(conf.getClassByName(codecClass), conf);
    } catch (ClassNotFoundException cnfe) {
      throw new IOException("Illegal codec!");
    }
    LOG.info("Created a Codec object of type: " + codecClass);

    // Generate data
    DataOutputBuffer data = new DataOutputBuffer();
    RandomDatum.Generator generator = new RandomDatum.Generator(seed);
    for(int i=0; i < count; ++i) {
      generator.next();
      RandomDatum key = generator.getKey();
      RandomDatum value = generator.getValue();
      
      key.write(data);
      value.write(data);
    }
    LOG.info("Generated " + count + " records");
    
    // Compress data
    DataOutputBuffer compressedDataBuffer = new DataOutputBuffer();
    int leasedCompressorsBefore = codec.getCompressorType() == null ? -1
        : CodecPool.getLeasedCompressorsCount(codec);
    try (CompressionOutputStream deflateFilter =
      codec.createOutputStream(compressedDataBuffer);
      DataOutputStream deflateOut =
        new DataOutputStream(new BufferedOutputStream(deflateFilter))) {
      deflateOut.write(data.getData(), 0, data.getLength());
      deflateOut.flush();
      deflateFilter.finish();
    }
    if (leasedCompressorsBefore > -1) {
      assertEquals("leased compressor not returned to the codec pool",
          leasedCompressorsBefore, CodecPool.getLeasedCompressorsCount(codec));
    }
    LOG.info("Finished compressing data");
    
    // De-compress data
    DataInputBuffer deCompressedDataBuffer = new DataInputBuffer();
    deCompressedDataBuffer.reset(compressedDataBuffer.getData(), 0, 
                                 compressedDataBuffer.getLength());
    DataInputBuffer originalData = new DataInputBuffer();
    int leasedDecompressorsBefore =
        CodecPool.getLeasedDecompressorsCount(codec);
    try (CompressionInputStream inflateFilter =
      codec.createInputStream(deCompressedDataBuffer);
      DataInputStream inflateIn =
        new DataInputStream(new BufferedInputStream(inflateFilter))) {

      // Check
      originalData.reset(data.getData(), 0, data.getLength());
      DataInputStream originalIn =
          new DataInputStream(new BufferedInputStream(originalData));
      for(int i=0; i < count; ++i) {
        RandomDatum k1 = new RandomDatum();
        RandomDatum v1 = new RandomDatum();
        k1.readFields(originalIn);
        v1.readFields(originalIn);
      
        RandomDatum k2 = new RandomDatum();
        RandomDatum v2 = new RandomDatum();
        k2.readFields(inflateIn);
        v2.readFields(inflateIn);
        assertTrue("original and compressed-then-decompressed-output not equal",
                   k1.equals(k2) && v1.equals(v2));
      
        // original and compressed-then-decompressed-output have the same
        // hashCode
        Map<RandomDatum, String> m = new HashMap<RandomDatum, String>();
        m.put(k1, k1.toString());
        m.put(v1, v1.toString());
        String result = m.get(k2);
        assertEquals("k1 and k2 hashcode not equal", result, k1.toString());
        result = m.get(v2);
        assertEquals("v1 and v2 hashcode not equal", result, v1.toString());
      }
    }
    assertEquals("leased decompressor not returned to the codec pool",
        leasedDecompressorsBefore,
        CodecPool.getLeasedDecompressorsCount(codec));

    // De-compress data byte-at-a-time
    originalData.reset(data.getData(), 0, data.getLength());
    deCompressedDataBuffer.reset(compressedDataBuffer.getData(), 0, 
                                 compressedDataBuffer.getLength());
    try (CompressionInputStream inflateFilter =
      codec.createInputStream(deCompressedDataBuffer);
      DataInputStream originalIn =
        new DataInputStream(new BufferedInputStream(originalData))) {

      // Check
      int expected;
      do {
        expected = originalIn.read();
        assertEquals("Inflated stream read by byte does not match",
            expected, inflateFilter.read());
      } while (expected != -1);
    }

    LOG.info("SUCCESS! Completed checking " + count + " records");
  }

};