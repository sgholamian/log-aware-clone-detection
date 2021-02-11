//,temp,UcsManagerImpl.java,117,128,temp,UcsManagerImpl.java,100,115
//,3
public class xxx {
        private void decommissionFadedBlade(Map<String, UcsBladeVO> previous, Map<String, ComputeBlade> now) {
            for (Map.Entry<String, UcsBladeVO> e : previous.entrySet()) {
                String dn = e.getKey();
                if (now.keySet().contains(dn)) {
                    continue;
                }

                UcsBladeVO vo = e.getValue();
                bladeDao.remove(vo.getId());
                s_logger.debug(String.format("decommission faded blade[dn:%s] during sync", vo.getDn()));
            }
        }

};