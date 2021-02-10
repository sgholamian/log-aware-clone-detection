//,temp,MergeManagerImpl.java,520,584,temp,MergeManagerImpl.java,437,508
//,3
public class xxx {
    @Override
    public void merge(List<InMemoryMapOutput<K,V>> inputs) throws IOException {
      if (inputs == null || inputs.size() == 0) {
        return;
      }
      
      //name this output file same as the name of the first file that is 
      //there in the current list of inmem files (this is guaranteed to
      //be absent on the disk currently. So we don't overwrite a prev. 
      //created spill). Also we need to create the output file now since
      //it is not guaranteed that this file will be present after merge
      //is called (we delete empty files as soon as we see them
      //in the merge method)

      //figure out the mapId 
      TaskAttemptID mapId = inputs.get(0).getMapId();
      TaskID mapTaskId = mapId.getTaskID();

      List<Segment<K, V>> inMemorySegments = new ArrayList<Segment<K, V>>();
      long mergeOutputSize = 
        createInMemorySegments(inputs, inMemorySegments,0);
      int noInMemorySegments = inMemorySegments.size();

      Path outputPath = 
        mapOutputFile.getInputFileForWrite(mapTaskId,
                                           mergeOutputSize).suffix(
                                               Task.MERGED_OUTPUT_PREFIX);

      FSDataOutputStream out = CryptoUtils.wrapIfNecessary(jobConf, rfs.create(outputPath));
      Writer<K, V> writer = new Writer<K, V>(jobConf, out,
          (Class<K>) jobConf.getMapOutputKeyClass(),
          (Class<V>) jobConf.getMapOutputValueClass(), codec, null, true);

      RawKeyValueIterator rIter = null;
      CompressAwarePath compressAwarePath;
      try {
        LOG.info("Initiating in-memory merge with " + noInMemorySegments + 
                 " segments...");
        
        rIter = Merger.merge(jobConf, rfs,
                             (Class<K>)jobConf.getMapOutputKeyClass(),
                             (Class<V>)jobConf.getMapOutputValueClass(),
                             inMemorySegments, inMemorySegments.size(),
                             new Path(reduceId.toString()),
                             (RawComparator<K>)jobConf.getOutputKeyComparator(),
                             reporter, spilledRecordsCounter, null, null);
        
        if (null == combinerClass) {
          Merger.writeFile(rIter, writer, reporter, jobConf);
        } else {
          combineCollector.setWriter(writer);
          combineAndSpill(rIter, reduceCombineInputCounter);
        }
        writer.close();
        compressAwarePath = new CompressAwarePath(outputPath,
            writer.getRawLength(), writer.getCompressedLength());

        LOG.info(reduceId +  
            " Merge of the " + noInMemorySegments +
            " files in-memory complete." +
            " Local file is " + outputPath + " of size " + 
            localFS.getFileStatus(outputPath).getLen());
      } catch (IOException e) { 
        //make sure that we delete the ondisk file that we created 
        //earlier when we invoked cloneFileAttributes
        localFS.delete(outputPath, true);
        throw e;
      }

      // Note the output of the merge
      closeOnDiskFile(compressAwarePath);
    }

};