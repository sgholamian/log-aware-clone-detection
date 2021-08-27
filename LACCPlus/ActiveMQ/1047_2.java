//,temp,CallerBufferingDataFileAppender.java,88,206,temp,DataFileAppender.java,254,379
//,3
public class xxx {
    protected void processQueue() {
        DataFile dataFile = null;
        RecoverableRandomAccessFile file = null;
        WriteBatch wb = null;
        try (DataByteArrayOutputStream buff = new DataByteArrayOutputStream(maxWriteBatchSize);) {

            while (true) {

                // Block till we get a command.
                synchronized (enqueueMutex) {
                    while (true) {
                        if (nextWriteBatch != null) {
                            wb = nextWriteBatch;
                            nextWriteBatch = null;
                            break;
                        }
                        if (shutdown) {
                            return;
                        }
                        enqueueMutex.wait();
                    }
                    enqueueMutex.notifyAll();
                }

                if (dataFile != wb.dataFile) {
                    if (file != null) {
                        if (periodicSync) {
                            if (logger.isTraceEnabled()) {
                                logger.trace("Syncing file {} on rotate", dataFile.getFile().getName());
                            }
                            file.sync();
                        }
                        dataFile.closeRandomAccessFile(file);
                    }
                    dataFile = wb.dataFile;
                    file = dataFile.appendRandomAccessFile();
                }

                Journal.WriteCommand write = wb.writes.getHead();

                // Write an empty batch control record.
                buff.reset();
                buff.write(EMPTY_BATCH_CONTROL_RECORD);

                boolean forceToDisk = false;
                while (write != null) {
                    forceToDisk |= write.sync | (syncOnComplete && write.onComplete != null);
                    buff.writeInt(write.location.getSize());
                    buff.writeByte(write.location.getType());
                    buff.write(write.data.getData(), write.data.getOffset(), write.data.getLength());
                    write = write.getNext();
                }

                // append 'unset', zero length next batch so read can always find eof
                buff.write(Journal.EOF_RECORD);

                ByteSequence sequence = buff.toByteSequence();

                // Now we can fill in the batch control record properly.
                buff.reset();
                buff.skip(RECORD_HEAD_SPACE + Journal.BATCH_CONTROL_RECORD_MAGIC.length);
                buff.writeInt(sequence.getLength() - Journal.BATCH_CONTROL_RECORD_SIZE - Journal.EOF_RECORD.length);
                if( journal.isChecksum() ) {
                    Checksum checksum = new Adler32();
                    checksum.update(sequence.getData(), sequence.getOffset()+Journal.BATCH_CONTROL_RECORD_SIZE, sequence.getLength()-Journal.BATCH_CONTROL_RECORD_SIZE-Journal.EOF_RECORD.length);
                    buff.writeLong(checksum.getValue());
                }

                // Now do the 1 big write.
                file.seek(wb.offset);
                if (maxStat > 0) {
                    if (statIdx < maxStat) {
                        stats[statIdx++] = sequence.getLength();
                    } else {
                        long all = 0;
                        for (;statIdx > 0;) {
                            all+= stats[--statIdx];
                        }
                        logger.info("Ave writeSize: " + all/maxStat);
                    }
                }
                file.write(sequence.getData(), sequence.getOffset(), sequence.getLength());

                ReplicationTarget replicationTarget = journal.getReplicationTarget();
                if( replicationTarget!=null ) {
                    replicationTarget.replicate(wb.writes.getHead().location, sequence, forceToDisk);
                }

                if (forceToDisk) {
                    file.sync();
                }

                Journal.WriteCommand lastWrite = wb.writes.getTail();
                journal.setLastAppendLocation(lastWrite.location);

                signalDone(wb);
            }
        } catch (Throwable error) {
            logger.warn("Journal failed while writing at: " + wb.dataFile.getDataFileId() + ":" + wb.offset, error);
            synchronized (enqueueMutex) {
                shutdown = true;
                running = false;
                signalError(wb, error);
                if (nextWriteBatch != null) {
                    signalError(nextWriteBatch, error);
                    nextWriteBatch = null;
                    enqueueMutex.notifyAll();
                }
            }
        } finally {
            try {
                if (file != null) {
                    if (periodicSync) {
                        if (logger.isTraceEnabled()) {
                            logger.trace("Syning file {} on close", dataFile.getFile().getName());
                        }
                        file.sync();
                    }
                    dataFile.closeRandomAccessFile(file);
                }
            } catch (Throwable ignore) {
            }
            shutdownDone.countDown();
            running = false;
        }
    }

};