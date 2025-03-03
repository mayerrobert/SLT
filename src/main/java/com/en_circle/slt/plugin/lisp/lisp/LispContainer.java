package com.en_circle.slt.plugin.lisp.lisp;

import java.util.List;

public class LispContainer implements LispElement {

    private final List<LispElement> items;
    private final ContainerType containerType;

    public LispContainer(List<LispElement> items, ContainerType containerType) {
        this.items = List.copyOf(items);
        this.containerType = containerType;
    }

    public List<LispElement> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStartingSymbol());
        for (int i=0; i<items.size(); i++) {
            sb.append(items.get(i));
            if (containerType == ContainerType.PAIR && i == items.size() - 1) {
                sb.append(" . ");
            } else {
                sb.append(" ");
            }
        }
        sb.append(getEndingSymbol());
        return sb.toString();
    }

    @Override
    public String toPrettyString() {
        return toPrettyStringInternal(0);
    }

    private String toPrettyStringInternal(int offset) {
        StringBuilder sb = new StringBuilder();
        if (offset > 0)
            sb.append("\n");
        for (int i=0; i<offset; i++)
            sb.append(" ");
        sb.append(getStartingSymbol());
        for (int i=0; i<items.size(); i++) {
            if (items.get(i) instanceof LispContainer) {
                sb.append(((LispContainer) items.get(i)).toPrettyStringInternal(offset + 2));
            } else {
                sb.append(items.get(i));
            }
            if (containerType == ContainerType.PAIR && i == items.size() - 1) {
                sb.append(" . ");
            } else if (i != items.size() - 1){
                sb.append(" ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private String getStartingSymbol() {
        switch (containerType) {
            case LIST:
            case PAIR:
                return "(";
            case VECTOR:
                return "#(";
        }
        throw new IllegalStateException();
    }

    private String getEndingSymbol() {
        switch (containerType) {
            case LIST:
            case PAIR:
            case VECTOR:
                return ")";
        }
        throw new IllegalStateException();
    }

    @Override
    public LispElementType getType() {
        return LispElementType.CONTAINER;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public enum ContainerType {
        LIST, PAIR, VECTOR
    }
}
