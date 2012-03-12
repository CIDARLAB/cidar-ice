package org.jbei.ice.client.search.advanced;

import java.util.ArrayList;
import java.util.Set;

import org.jbei.ice.client.RegistryServiceAsync;
import org.jbei.ice.client.common.EntryDataViewDataProvider;
import org.jbei.ice.client.event.EntryViewEvent;
import org.jbei.ice.client.event.EntryViewEvent.EntryViewEventHandler;
import org.jbei.ice.client.event.SearchEvent;
import org.jbei.ice.client.event.SearchEventHandler;
import org.jbei.ice.client.search.blast.BlastSearchDataProvider;
import org.jbei.ice.client.search.event.AdvancedSearchEvent;
import org.jbei.ice.shared.QueryOperator;
import org.jbei.ice.shared.dto.BlastResultInfo;
import org.jbei.ice.shared.dto.SearchFilterInfo;

import com.google.gwt.event.shared.HandlerManager;

public class AdvancedSearchPresenter {

    // again another temp measure which will be replaced by EntryContext
    private enum Mode {
        BLAST, SEARCH;
    }

    private final AdvancedSearchView display;
    private final EntryDataViewDataProvider dataProvider;
    private final BlastSearchDataProvider blastProvider;
    private final AdvancedSearchModel model;
    private AdvancedSearchResultsTable table;
    private Mode mode;

    public AdvancedSearchPresenter(RegistryServiceAsync rpcService, HandlerManager eventBus) {
        this.display = new AdvancedSearchView();

        table = new AdvancedSearchResultsTable() {

            @Override
            protected EntryViewEventHandler getHandler() {
                return new EntryViewEventHandler() {
                    @Override
                    public void onEntryView(EntryViewEvent event) {
                        event.setList(dataProvider.getData());
                        model.getEventBus().fireEvent(event);
                    }
                };
            }
        };

        // hide the results table
        dataProvider = new AdvancedSearchDataProvider(table, rpcService);
        blastProvider = new BlastSearchDataProvider(display.getBlastResultTable(),
                new ArrayList<BlastResultInfo>(), rpcService);

        this.model = new AdvancedSearchModel(rpcService, eventBus);

        // register for search events
        eventBus.addHandler(SearchEvent.TYPE, new SearchEventHandler() {

            @Override
            public void onSearch(SearchEvent event) {
                search(event.getFilters());
            }
        });

    }

    public AdvancedSearchPresenter(final RegistryServiceAsync rpcService,
            final HandlerManager eventBus, ArrayList<SearchFilterInfo> operands) {
        this(rpcService, eventBus);
        search(operands);
    }

    protected void search(final ArrayList<SearchFilterInfo> searchFilters) {
        if (searchFilters == null)
            return;

        // currently support only a single blast search with filters
        // search for blast operator
        SearchFilterInfo blastInfo = null;
        for (SearchFilterInfo filter : searchFilters) {
            QueryOperator operator = QueryOperator.operatorValueOf(filter.getOperator());
            if (operator == null)
                continue;

            if (operator == QueryOperator.TBLAST_X || operator == QueryOperator.BLAST_N) {
                if (searchFilters.remove(filter)) {
                    blastInfo = filter;
                }
                break;
            }
        }

        if (blastInfo != null) {

            // show blast table loading
            blastProvider.updateRowCount(0, false);
            display.setBlastVisibility(true);
            display.getBlastResultTable().setVisibleRangeAndClearData(
                display.getBlastResultTable().getVisibleRange(), false);

            // get blast results and filter 
            QueryOperator program = QueryOperator.operatorValueOf(blastInfo.getOperator());
            this.model.performBlast(searchFilters, blastInfo.getOperand(), program, new Handler(
                    searchFilters));
        } else {
            display.setSearchVisibility(table, true);
            table.setVisibleRangeAndClearData(table.getVisibleRange(), false);

            this.model.retrieveSearchResults(searchFilters, new Handler(searchFilters));
        }
    }

    public Set<Long> getEntrySet() {
        switch (mode) {
        case SEARCH:
        default:
            return table.getSelectedEntrySet();

        case BLAST:
            return display.getBlastResultTable().getSelectedEntrySet();
        }

    }

    public AdvancedSearchView getView() {
        return this.display;
    }

    // 
    // inner class
    //

    private class Handler implements AdvancedSearchEvent.AdvancedSearchEventHandler {

        private final ArrayList<SearchFilterInfo> searchFilters;

        public Handler(ArrayList<SearchFilterInfo> filters) {
            this.searchFilters = filters;
        }

        @Override
        public void onSearchCompletion(AdvancedSearchEvent event) {
            display.setSearchFilters(searchFilters);
            dataProvider.setValues(event.getSearchResults());
            mode = Mode.SEARCH;
        }

        @Override
        public void onBlastCompletion(AdvancedSearchEvent event) {
            display.setSearchFilters(searchFilters);
            blastProvider.setData(event.getResults());
            mode = Mode.BLAST;
        }
    }
}
