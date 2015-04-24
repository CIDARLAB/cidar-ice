package org.jbei.ice.lib.dto.entry;

import org.jbei.ice.lib.dao.IDataTransferModel;

public enum EntryType implements IDataTransferModel {

    STRAIN("Strain", "strain"),
    PLASMID("Plasmid", "plasmid"),
    PART("Part", "part"),
    ARABIDOPSIS("Arabidopsis", "arabidopsis");

    private String name;
    private String display;
    public static final long serialVersionUID = 1l;

    EntryType(String display, String name) {
        this.display = display;
        this.name = name;
    }

    public static EntryType nameToType(String name) {
        name = name.trim();
        for (EntryType type : EntryType.values()) {
            if (name.equalsIgnoreCase(type.getName()))
                return type;
        }

        return null;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplay() {
        return this.display;
    }

    @Override
    public String toString() {
        return this.display;
    }
}