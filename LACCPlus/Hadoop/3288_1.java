//,temp,TestCompressionStreamReuse.java,81,173,temp,TestCodec.java,179,287
//,3
public class xxx {
  private void resetStateTest(Configuration conf, int seed, int count,
      String codecClass) throws IOException {
    // Create the codec
    CompressionCodec codec = null;
    try {
      codec = (CompressionCodec) ReflectionUtils.newInstance(conf
          .getClassByName(codecClass), conf);
    } catch (ClassNotFoundException cnfe) {
      throw new IOException("Illegal codec!");
    }
    LOG.info("Created a Codec object of type: " + codecClass);

    // Generate data
    DataOutputBuffer data = new DataOutputBuffer();
    RandomDatum.Generator generator = new RandomDatum.Generator(seed);
    for (int i = 0; i < count; ++i) {
      generator.next();
      RandomDatum key = generator.getKey();
      RandomDatum value = generator.getValue();

      key.write(data);
      value.write(data);
    }
    LOG.info("Generated " + count + " records");

    // Compress data
    DataOutputBuffer compressedDataBuffer = new DataOutputBuffer();
    DataOutputStream deflateOut = new DataOutputStream(
        new BufferedOutputStream(compressedDataBuffer));
    CompressionOutputStream deflateFilter = codec
        .createOutputStream(deflateOut);
    deflateFilter.write(data.getData(), 0, data.getLength());
    deflateFilter.finish();
    deflateFilter.flush();
    LOG.info("Finished compressing data");

    // reset deflator
    deflateFilter.resetState();
    LOG.info("Finished reseting deflator");

    // re-generate data
    data.reset();
    generator = new RandomDatum.Generator(seed);
    for (int i = 0; i < count; ++i) {
      generator.next();
      RandomDatum key = generator.getKey();
      RandomDatum value = generator.getValue();

      key.write(data);
      value.write(data);
    }
    DataInputBuffer originalData = new DataInputBuffer();
    DataInputStream originalIn = new DataInputStream(
        new BufferedInputStream(originalData));
    originalData.reset(data.getData(), 0, data.getLength());

    // re-compress data
    compressedDataBuffer.reset();
    deflateOut = new DataOutputStream(new BufferedOutputStream(
        compressedDataBuffer));
    deflateFilter = codec.createOutputStream(deflateOut);

    deflateFilter.write(data.getData(), 0, data.getLength());
    deflateFilter.finish();
    deflateFilter.flush();
    LOG.info("Finished re-compressing data");

    // De-compress data
    DataInputBuffer deCompressedDataBuffer = new DataInputBuffer();
    deCompressedDataBuffer.reset(compressedDataBuffer.getData(), 0,
        compressedDataBuffer.getLength());
    CompressionInputStream inflateFilter = codec
        .createInputStream(deCompressedDataBuffer);
    DataInputStream inflateIn = new DataInputStream(
        new BufferedInputStream(inflateFilter));

    // Check
    for (int i = 0; i < count; ++i) {
      RandomDatum k1 = new RandomDatum();
      RandomDatum v1 = new RandomDatum();
      k1.readFields(originalIn);
      v1.readFields(originalIn);

      RandomDatum k2 = new RandomDatum();
      RandomDatum v2 = new RandomDatum();
      k2.readFields(inflateIn);
      v2.readFields(inflateIn);
      assertTrue(
          "original and compressed-then-decompressed-output not equal",
          k1.equals(k2) && v1.equals(v2));
    }
    LOG.info("SUCCESS! Completed checking " + count + " records");
  }

};