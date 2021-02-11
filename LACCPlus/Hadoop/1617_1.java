//,temp,MergeManagerImpl.java,520,584,temp,MergeManagerImpl.java,437,508
//,3
public class xxx {
    @Override
    public void merge(List<CompressAwarePath> inputs) throws IOException {
      // sanity check
      if (inputs == null || inputs.isEmpty()) {
        LOG.info("No ondisk files to merge...");
        return;
      }
      
      long approxOutputSize = 0;
      int bytesPerSum = 
        jobConf.getInt("io.bytes.per.checksum", 512);
      
      LOG.info("OnDiskMerger: We have  " + inputs.size() + 
               " map outputs on disk. Triggering merge...");
      
      // 1. Prepare the list of files to be merged. 
      for (CompressAwarePath file : inputs) {
        approxOutputSize += localFS.getFileStatus(file).getLen();
      }

      // add the checksum length
      approxOutputSize += 
        ChecksumFileSystem.getChecksumLength(approxOutputSize, bytesPerSum);

      // 2. Start the on-disk merge process
      Path outputPath = 
        localDirAllocator.getLocalPathForWrite(inputs.get(0).toString(), 
            approxOutputSize, jobConf).suffix(Task.MERGED_OUTPUT_PREFIX);

      FSDataOutputStream out = CryptoUtils.wrapIfNecessary(jobConf, rfs.create(outputPath));
      Writer<K, V> writer = new Writer<K, V>(jobConf, out,
          (Class<K>) jobConf.getMapOutputKeyClass(),
          (Class<V>) jobConf.getMapOutputValueClass(), codec, null, true);

      RawKeyValueIterator iter  = null;
      CompressAwarePath compressAwarePath;
      Path tmpDir = new Path(reduceId.toString());
      try {
        iter = Merger.merge(jobConf, rfs,
                            (Class<K>) jobConf.getMapOutputKeyClass(),
                            (Class<V>) jobConf.getMapOutputValueClass(),
                            codec, inputs.toArray(new Path[inputs.size()]), 
                            true, ioSortFactor, tmpDir, 
                            (RawComparator<K>) jobConf.getOutputKeyComparator(), 
                            reporter, spilledRecordsCounter, null, 
                            mergedMapOutputsCounter, null);

        Merger.writeFile(iter, writer, reporter, jobConf);
        writer.close();
        compressAwarePath = new CompressAwarePath(outputPath,
            writer.getRawLength(), writer.getCompressedLength());
      } catch (IOException e) {
        localFS.delete(outputPath, true);
        throw e;
      }

      closeOnDiskFile(compressAwarePath);

      LOG.info(reduceId +
          " Finished merging " + inputs.size() + 
          " map output files on disk of total-size " + 
          approxOutputSize + "." + 
          " Local output file is " + outputPath + " of size " +
          localFS.getFileStatus(outputPath).getLen());
    }

};