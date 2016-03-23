package com.kuvshinov.graph.core.graph.node;

import com.kuvshinov.graph.core.graph.DataType;

import java.util.List;

/**
 * @author Sergey Kuvshinov
 */
public interface NodeDefinition {

    List<String> getAttributeNames();

    Attribute getAttribute(String attributeName);

    void addAttribute(String attributeName, DataType dataType, Object value);

    void setAttribute(String attributeName, Object value);

}
