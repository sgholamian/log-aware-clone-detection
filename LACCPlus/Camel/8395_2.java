//,temp,HBaseIdempotentRepository.java,76,87,temp,HBaseIdempotentRepository.java,59,74
//,3
public class xxx {
    @Override
    public boolean add(String o) {
        try {
            if (contains(o)) {
                return false;
            }
            byte[] b = HBaseHelper.toBytes(o);
            Put put = new Put(b);
            put.addColumn(HBaseHelper.getHBaseFieldAsBytes(family), HBaseHelper.getHBaseFieldAsBytes(qualifier), b);
            table.put(put);
            return true;
        } catch (Exception e) {
            LOG.warn("Error adding object {} to HBase repository.", o);
            return false;
        }
    }

};