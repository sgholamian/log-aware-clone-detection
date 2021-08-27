//,temp,AdviceWithTasks.java,261,290,temp,AdviceWithTasks.java,195,226
//,3
public class xxx {
    private static AdviceWithTask doRemove(
            final RouteDefinition route, final MatchBy matchBy, boolean selectFirst, boolean selectLast, int selectFrom,
            int selectTo, int maxDeep) {
        return new AdviceWithTask() {
            public void task() throws Exception {
                boolean match = false;
                Iterator<ProcessorDefinition<?>> it = AdviceWithTasks.createMatchByIterator(route, matchBy, selectFirst,
                        selectLast, selectFrom, selectTo, maxDeep);
                while (it.hasNext()) {
                    ProcessorDefinition<?> output = it.next();
                    if (matchBy.match(output)) {
                        List<ProcessorDefinition<?>> outputs = getOutputs(output, route);
                        if (outputs != null) {
                            int index = outputs.indexOf(output);
                            if (index != -1) {
                                match = true;
                                Object old = outputs.remove(index);
                                LOG.info("AdviceWith ({}) : [{}] --> remove", matchBy.getId(), old);
                            }
                        }
                    }
                }

                if (!match) {
                    throw new IllegalArgumentException(
                            "There are no outputs which matches: " + matchBy.getId() + " in the route: " + route);
                }
            }
        };
    }

};