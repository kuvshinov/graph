package com.kuvshinov.graph.core.graph.node;

import com.kuvshinov.graph.core.graph.DataType;

/**
 * @author Sergey Kuvshinov
 */
public class Attribute {

    private Object value;

    private DataType type;

    public Object getValue() {
        return value;
    }

    public DataType getType() {
        return type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setType(DataType type) {
        this.type = type;
    }
}
