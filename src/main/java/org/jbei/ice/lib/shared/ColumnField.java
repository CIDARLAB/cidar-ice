package org.jbei.ice.lib.shared;

import org.jbei.ice.lib.dao.IDataTransferModel;

/**
 * Column fields for tables used on the ice platform UI
 *
 * @author Hector Plahar
 */
public enum ColumnField implements IDataTransferModel {

    SELECTION("Select"),
    TYPE("Type"),
    PART_ID("Part ID"),
    CREATED("Created"),
    NAME("Name"),
    SUMMARY("Summary"),
    REGISTRY_NAME("Registry Name"),
    OWNER("Owner"),
    STATUS("Status"),
    BIT_SCORE("Bit Score"),
    E_VALUE("E-Value"),
    ALIGNED_BP("Aligned (BP)"),
    ALIGNED_IDENTITY("Aligned % Identity"),
    LAST_ADDED("Last Added"),
    LAST_VISITED("Last Visited"),
    LABEL("Label"),
    NOTES("Notes"),
    LOCATION("Location"),
    DESCRIPTION("Description"),
    COUNT("Member Count"),
    RELEVANCE("Relevance"),
    ALIGNMENT("Alignment");

    private String name;

    ColumnField(String name) {
        this.name = name;
    }

    private ColumnField() {
    }

    public String getName() {
        return this.name;
    }
}
