//,temp,ApiMethodHelper.java,241,267,temp,FacebookMethodsTypeHelper.java,117,135
//,3
public class xxx {
    public List<ApiMethod> getCandidateMethods(String name, Collection<String> argNames) {
        List<T> methods = methodMap.get(name);
        if (methods == null) {
            if (aliasesMap.containsKey(name)) {
                methods = new ArrayList<>();
                for (String method : aliasesMap.get(name)) {
                    methods.addAll(methodMap.get(method));
                }
            }
        }
        if (methods == null) {
            LOG.debug("No matching method for method {}", name);
            return Collections.emptyList();
        }
        int nArgs = argNames != null ? argNames.size() : 0;
        if (nArgs == 0) {
            LOG.debug("Found {} methods for method {}", methods.size(), name);
            return Collections.unmodifiableList(methods);
        } else {
            final List<ApiMethod> filteredSet = filterMethods(methods, MatchType.SUBSET, argNames);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Found {} filtered methods for {}",
                        filteredSet.size(), name + argNames.toString().replace('[', '(').replace(']', ')'));
            }
            return filteredSet;
        }
    }

};