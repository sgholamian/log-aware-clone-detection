//,temp,TestAtomicOperation.java,532,588,temp,TestAtomicOperation.java,439,491
//,3
public class xxx {
        @Override
        public void run() {
          boolean op = true;
          for (int i=0; i<numOps; i++) {
            try {
              // throw in some flushes
              if (i%10==0) {
                synchronized(region) {
                  LOG.debug("flushing");
                  region.flush(true);
                  if (i%100==0) {
                    region.compact(false);
                  }
                }
              }
              long ts = timeStamps.incrementAndGet();
              List<Mutation> mrm = new ArrayList<>();
              if (op) {
                Put p = new Put(row2, ts);
                p.addColumn(fam1, qual1, value1);
                p.setDurability(Durability.ASYNC_WAL);
                mrm.add(p);
                Delete d = new Delete(row);
                d.addColumns(fam1, qual1, ts);
                d.setDurability(Durability.ASYNC_WAL);
                mrm.add(d);
              } else {
                Delete d = new Delete(row2);
                d.addColumns(fam1, qual1, ts);
                d.setDurability(Durability.ASYNC_WAL);
                mrm.add(d);
                Put p = new Put(row, ts);
                p.setDurability(Durability.ASYNC_WAL);
                p.addColumn(fam1, qual1, value2);
                mrm.add(p);
              }
              region.mutateRowsWithLocks(mrm, rowsToLock, HConstants.NO_NONCE, HConstants.NO_NONCE);
              op ^= true;
              // check: should always see exactly one column
              Scan s = new Scan(row);
              RegionScanner rs = region.getScanner(s);
              List<Cell> r = new ArrayList<>();
              while (rs.next(r))
                ;
              rs.close();
              if (r.size() != 1) {
                LOG.debug(Objects.toString(r));
                failures.incrementAndGet();
                fail();
              }
            } catch (IOException e) {
              e.printStackTrace();
              failures.incrementAndGet();
              fail();
            }
          }
        }

};