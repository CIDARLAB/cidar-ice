package org.jbei.ice.client.admin.usermanagement;

import org.jbei.ice.shared.dto.AccountInfo;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

public class UserTable extends CellTable<AccountInfo> {

    protected interface EntryResources extends Resources {

        static EntryResources INSTANCE = GWT.create(EntryResources.class);

        /**
         * The styles used in this widget.
         */
        @Override
        @Source("org/jbei/ice/client/resource/css/EntryTable.css")
        Style cellTableStyle();
    }

    private SelectionModel<AccountInfo> selectionModel;

    public UserTable() {
        super(15, EntryResources.INSTANCE);
        setStyleName("data_table");
        Label empty = new Label();
        empty.setText("No data available");
        empty.setStyleName("no_data_style");
        this.setEmptyTableWidget(empty);
        setSelectionModel();
        createColumns();

        /* 
         * ListHandler<ContactInfo> sortHandler =
        new ListHandler<ContactInfo>(ContactDatabase.get().getDataProvider().getList());
        dataGrid.addColumnSortHandler(sortHandler);
         */
    }

    /**
     * Adds a selection model so cells can be selected
     */
    private void setSelectionModel() {
        selectionModel = new MultiSelectionModel<AccountInfo>(new ProvidesKey<AccountInfo>() {

            @Override
            public String getKey(AccountInfo item) {
                return item.getEmail();
            }
        });

        setSelectionModel(selectionModel,
            DefaultSelectionEventManager.<AccountInfo> createCheckboxManager());
    }

    private void createColumns() {
        createSelectionColumn();
        createFirstNameColumn();
        createLastNameColumn();
        createEmailColumn();
        createEntryCountColumn();
    }

    private void createSelectionColumn() {
        Column<AccountInfo, Boolean> checkColumn = new Column<AccountInfo, Boolean>(
                new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(AccountInfo object) {
                return selectionModel.isSelected(object);
            }
        };
        addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
        setColumnWidth(checkColumn, 40, Unit.PX);
    }

    private void createFirstNameColumn() {
        Column<AccountInfo, String> firstNameColumn = new Column<AccountInfo, String>(
                new EditTextCell()) {
            @Override
            public String getValue(AccountInfo object) {
                return object.getFirstName();
            }
        };

        firstNameColumn.setSortable(false);
        //        sortHandler.setComparator(firstNameColumn, new Comparator<AccountInfo>() {
        //            public int compare(AccountInfo o1, AccountInfo o2) {
        //                return o1.getFirstName().compareTo(o2.getFirstName());
        //            }
        //        });

        addColumn(firstNameColumn, "First Name");
        firstNameColumn.setFieldUpdater(new FieldUpdater<AccountInfo, String>() {
            public void update(int index, AccountInfo object, String value) {
                // Called when the user changes the value.
                object.setFirstName(value);
                //                ContactDatabase.get().refreshDisplays();
            }
        });

        setColumnWidth(firstNameColumn, 20, Unit.PCT);
    }

    private void createLastNameColumn() {

        Column<AccountInfo, String> lastName = new Column<AccountInfo, String>(new EditTextCell()) {
            @Override
            public String getValue(AccountInfo object) {
                return object.getLastName();
            }
        };

        lastName.setSortable(false);
        //        sortHandler.setComparator(firstNameColumn, new Comparator<AccountInfo>() {
        //            public int compare(AccountInfo o1, AccountInfo o2) {
        //                return o1.getFirstName().compareTo(o2.getFirstName());
        //            }
        //        });

        addColumn(lastName, "Last Name");
        lastName.setFieldUpdater(new FieldUpdater<AccountInfo, String>() {
            public void update(int index, AccountInfo object, String value) {
                // Called when the user changes the value.
                object.setLastName(value);
                //                ContactDatabase.get().refreshDisplays(); // TODO
            }
        });
        setColumnWidth(lastName, 20, Unit.PCT);
    }

    private void createEmailColumn() {
        Column<AccountInfo, String> email = new Column<AccountInfo, String>(new TextCell()) {

            @Override
            public String getValue(AccountInfo object) {
                return object.getEmail();
            }
        };
        addColumn(email, "Email");
        setColumnWidth(email, 30, Unit.PT);
    }

    private void createEntryCountColumn() {
        Column<AccountInfo, String> entryCount = new Column<AccountInfo, String>(new TextCell()) {

            @Override
            public String getValue(AccountInfo object) {
                return object.getUserEntryCount() + "";
            }
        };
        addColumn(entryCount, "Entries");
        setColumnWidth(entryCount, 50, Unit.PX);
    }
}
