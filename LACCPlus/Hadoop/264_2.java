//,temp,SequenceFile.java,2285,2318,temp,SequenceFile.java,2246,2278
//,3
public class xxx {
    public synchronized void getCurrentValue(Writable val) 
      throws IOException {
      if (val instanceof Configurable) {
        ((Configurable) val).setConf(this.conf);
      }

      // Position stream to 'current' value
      seekToCurrentValue();

      if (!blockCompressed) {
        val.readFields(valIn);
        
        if (valIn.read() > 0) {
          LOG.info("available bytes: " + valIn.available());
          throw new IOException(val+" read "+(valBuffer.getPosition()-keyLength)
                                + " bytes, should read " +
                                (valBuffer.getLength()-keyLength));
        }
      } else {
        // Get the value
        int valLength = WritableUtils.readVInt(valLenIn);
        val.readFields(valIn);
        
        // Read another compressed 'value'
        --noBufferedValues;
        
        // Sanity check
        if ((valLength < 0) && LOG.isDebugEnabled()) {
          LOG.debug(val + " is a zero-length value");
        }
      }

    }

};