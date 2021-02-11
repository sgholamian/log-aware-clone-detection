//,temp,UcsManagerImpl.java,117,128,temp,UcsManagerImpl.java,100,115
//,3
public class xxx {
        private void discoverNewBlades(Map<String, UcsBladeVO> previous, Map<String, ComputeBlade> now, UcsManagerVO mgr) {
            for (Map.Entry<String, ComputeBlade> e : now.entrySet()) {
                String dn = e.getKey();
                if (previous.keySet().contains(dn)) {
                    continue;
                }

                ComputeBlade nc = e.getValue();
                UcsBladeVO vo = new UcsBladeVO();
                vo.setDn(nc.getDn());
                vo.setUcsManagerId(mgr.getId());
                vo.setUuid(UUID.randomUUID().toString());
                bladeDao.persist(vo);
                s_logger.debug(String.format("discovered a new UCS blade[dn:%s] during sync", nc.getDn()));
            }
        }

};