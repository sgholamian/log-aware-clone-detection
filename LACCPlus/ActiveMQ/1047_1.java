//,temp,CallerBufferingDataFileAppender.java,88,206,temp,DataFileAppender.java,254,379
//,3
public class xxx {
    @Override
    protected void processQueue() {
        DataFile dataFile = null;
        RecoverableRandomAccessFile file = null;
        WriteBatch wb = null;
        try {

            while (true) {

                Object o = null;

                // Block till we get a command.
                synchronized (enqueueMutex) {
                    while (true) {
                        if (nextWriteBatch != null) {
                            o = nextWriteBatch;
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

                wb = (WriteBatch)o;
                if (dataFile != wb.dataFile) {
                    if (file != null) {
                        if (periodicSync) {
                            if (logger.isTraceEnabled()) {
                                logger.trace("Syning file {} on rotate", dataFile.getFile().getName());
                            }
                            file.sync();
                        }
                        dataFile.closeRandomAccessFile(file);
                    }
                    dataFile = wb.dataFile;
                    file = dataFile.openRandomAccessFile();
                }

                final DataByteArrayOutputStream buff = wb.buff;
                final boolean forceToDisk = wb.forceToDisk;

                ByteSequence sequence = buff.toByteSequence();

                // Now we can fill in the batch control record properly.
                buff.reset();
                buff.skip(5+Journal.BATCH_CONTROL_RECORD_MAGIC.length);
                buff.writeInt(sequence.getLength()-Journal.BATCH_CONTROL_RECORD_SIZE);
                if( journal.isChecksum() ) {
                    Checksum checksum = new Adler32();
                    checksum.update(sequence.getData(), sequence.getOffset()+Journal.BATCH_CONTROL_RECORD_SIZE, sequence.getLength()-Journal.BATCH_CONTROL_RECORD_SIZE);
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
                        System.err.println("Ave writeSize: " + all/maxStat);
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
        } catch (IOException e) {
            synchronized (enqueueMutex) {
                firstAsyncException = e;
                if (wb != null) {
                    wb.exception.set(e);
                    wb.latch.countDown();
                }
                if (nextWriteBatch != null) {
                    nextWriteBatch.exception.set(e);
                    nextWriteBatch.latch.countDown();
                }
            }
        } catch (InterruptedException e) {
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
                if (wb != null && wb.buff != null) {
                    wb.buff.close();
                }
            } catch (Throwable ignore) {
            }
            shutdownDone.countDown();
            running = false;
        }
    }

};