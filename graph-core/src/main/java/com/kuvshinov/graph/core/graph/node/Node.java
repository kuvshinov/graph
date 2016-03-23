package com.kuvshinov.graph.core.graph.node;

import java.util.List;

/**
 * @author Sergey Kuvshinov
 */
public interface Node {

    void addRelationNode(Node n);

    void removeRelationNode(Node n);

    void remove();

    String getId();

    List<Node> getRelationNodes();

}
