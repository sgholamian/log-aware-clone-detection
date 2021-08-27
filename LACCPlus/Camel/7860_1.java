//,temp,AdviceWithTasks.java,396,434,temp,AdviceWithTasks.java,190,228
//,2
public class xxx {
    private static AdviceWithTask doAfter(
            final RouteDefinition route, final MatchBy matchBy, final ProcessorDefinition<?> after, boolean selectFirst,
            boolean selectLast,
            int selectFrom, int selectTo, int maxDeep) {
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
                                // flattern as after uses a pipeline as
                                // temporary holder
                                ProcessorDefinition<?> flattern = flatternOutput(after);
                                Object existing = outputs.get(index);
                                outputs.add(index + 1, flattern);
                                // must set parent on the node we added in the
                                // route
                                ProcessorDefinition parent = output.getParent() != null ? output.getParent() : route;
                                flattern.setParent(parent);
                                LOG.info("AdviceWith ({}) : [{}] --> after [{}]", matchBy.getId(), existing, flattern);
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