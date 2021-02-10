//,temp,TestCryptoCodec.java,109,228,temp,TestCodec.java,168,259
//,3
public class xxx {
  private void cryptoCodecTest(Configuration conf, int seed, int count, 
      String encCodecClass, String decCodecClass, byte[] iv) throws IOException, 
      GeneralSecurityException {
    CryptoCodec encCodec = null;
    try {
      encCodec = (CryptoCodec)ReflectionUtils.newInstance(
          conf.getClassByName(encCodecClass), conf);
    } catch (ClassNotFoundException cnfe) {
      throw new IOException("Illegal crypto codec!");
    }
    LOG.info("Created a Codec object of type: " + encCodecClass);
    
    // Generate data
    DataOutputBuffer data = new DataOutputBuffer();
    RandomDatum.Generator generator = new RandomDatum.Generator(seed);
    for(int i = 0; i < count; ++i) {
      generator.next();
      RandomDatum key = generator.getKey();
      RandomDatum value = generator.getValue();
      
      key.write(data);
      value.write(data);
    }
    LOG.info("Generated " + count + " records");
    
    // Encrypt data
    DataOutputBuffer encryptedDataBuffer = new DataOutputBuffer();
    CryptoOutputStream out = new CryptoOutputStream(encryptedDataBuffer, 
        encCodec, bufferSize, key, iv);
    out.write(data.getData(), 0, data.getLength());
    out.flush();
    out.close();
    LOG.info("Finished encrypting data");
    
    CryptoCodec decCodec = null;
    try {
      decCodec = (CryptoCodec)ReflectionUtils.newInstance(
          conf.getClassByName(decCodecClass), conf);
    } catch (ClassNotFoundException cnfe) {
      throw new IOException("Illegal crypto codec!");
    }
    LOG.info("Created a Codec object of type: " + decCodecClass);
    
    // Decrypt data
    DataInputBuffer decryptedDataBuffer = new DataInputBuffer();
    decryptedDataBuffer.reset(encryptedDataBuffer.getData(), 0, 
        encryptedDataBuffer.getLength());
    CryptoInputStream in = new CryptoInputStream(decryptedDataBuffer, 
        decCodec, bufferSize, key, iv);
    DataInputStream dataIn = new DataInputStream(new BufferedInputStream(in));
    
    // Check
    DataInputBuffer originalData = new DataInputBuffer();
    originalData.reset(data.getData(), 0, data.getLength());
    DataInputStream originalIn = new DataInputStream(
        new BufferedInputStream(originalData));
    
    for(int i=0; i < count; ++i) {
      RandomDatum k1 = new RandomDatum();
      RandomDatum v1 = new RandomDatum();
      k1.readFields(originalIn);
      v1.readFields(originalIn);
      
      RandomDatum k2 = new RandomDatum();
      RandomDatum v2 = new RandomDatum();
      k2.readFields(dataIn);
      v2.readFields(dataIn);
      assertTrue("original and encrypted-then-decrypted-output not equal",
                 k1.equals(k2) && v1.equals(v2));
      
      // original and encrypted-then-decrypted-output have the same hashCode
      Map<RandomDatum, String> m = new HashMap<RandomDatum, String>();
      m.put(k1, k1.toString());
      m.put(v1, v1.toString());
      String result = m.get(k2);
      assertEquals("k1 and k2 hashcode not equal", result, k1.toString());
      result = m.get(v2);
      assertEquals("v1 and v2 hashcode not equal", result, v1.toString());
    }

    // Decrypt data byte-at-a-time
    originalData.reset(data.getData(), 0, data.getLength());
    decryptedDataBuffer.reset(encryptedDataBuffer.getData(), 0, 
        encryptedDataBuffer.getLength());
    in = new CryptoInputStream(decryptedDataBuffer, 
        decCodec, bufferSize, key, iv);

    // Check
    originalIn = new DataInputStream(new BufferedInputStream(originalData));
    int expected;
    do {
      expected = originalIn.read();
      assertEquals("Decrypted stream read by byte does not match",
        expected, in.read());
    } while (expected != -1);
    
    // Seek to a certain position and decrypt
    originalData.reset(data.getData(), 0, data.getLength());
    decryptedDataBuffer.reset(encryptedDataBuffer.getData(), 0,
        encryptedDataBuffer.getLength());
    in = new CryptoInputStream(new TestCryptoStreams.FakeInputStream(
        decryptedDataBuffer), decCodec, bufferSize, key, iv);
    int seekPos = data.getLength() / 3;
    in.seek(seekPos);
    
    // Check
    TestCryptoStreams.FakeInputStream originalInput = 
        new TestCryptoStreams.FakeInputStream(originalData);
    originalInput.seek(seekPos);
    do {
      expected = originalInput.read();
      assertEquals("Decrypted stream read by byte does not match",
        expected, in.read());
    } while (expected != -1);

    LOG.info("SUCCESS! Completed checking " + count + " records");
    
    // Check secure random generator
    testSecureRandom(encCodec);
  }

};