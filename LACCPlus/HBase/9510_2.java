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
              RowMutations rm = new RowMutations(row);
              if (op) {
                Put p = new Put(row, ts);
                p.addColumn(fam1, qual1, value1);
                p.setDurability(Durability.ASYNC_WAL);
                rm.add(p);
                Delete d = new Delete(row);
                d.addColumns(fam1, qual2, ts);
                d.setDurability(Durability.ASYNC_WAL);
                rm.add(d);
              } else {
                Delete d = new Delete(row);
                d.addColumns(fam1, qual1, ts);
                d.setDurability(Durability.ASYNC_WAL);
                rm.add(d);
                Put p = new Put(row, ts);
                p.addColumn(fam1, qual2, value2);
                p.setDurability(Durability.ASYNC_WAL);
                rm.add(p);
              }
              region.mutateRow(rm);
              op ^= true;
              // check: should always see exactly one column
              Get g = new Get(row);
              Result r = region.get(g);
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