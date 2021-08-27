//,temp,HBaseIdempotentRepository.java,76,87,temp,HBaseIdempotentRepository.java,59,74
//,3
public class xxx {
    @Override
    public boolean contains(String o) {
        try {
            byte[] b = HBaseHelper.toBytes(o);
            Get get = new Get(b);
            get.addColumn(HBaseHelper.getHBaseFieldAsBytes(family), HBaseHelper.getHBaseFieldAsBytes(qualifier));
            return table.exists(get);
        } catch (Exception e) {
            LOG.warn("Error reading object {} from HBase repository.", o);
            return false;
        }
    }

};