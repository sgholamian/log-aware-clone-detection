//,temp,MockVpcDaoImpl.java,110,126,temp,MockNetworkOfferingDaoImpl.java,137,153
//,2
public class xxx {
    private VpcVO setId(VpcVO vo, long id) {
        VpcVO voToReturn = vo;
        Class<?> c = voToReturn.getClass();
        try {
            Field f = c.getDeclaredField("id");
            f.setAccessible(true);
            f.setLong(voToReturn, id);
        } catch (NoSuchFieldException ex) {
            s_logger.warn(ex);
            return null;
        } catch (IllegalAccessException ex) {
            s_logger.warn(ex);
            return null;
        }

        return voToReturn;
    }

};