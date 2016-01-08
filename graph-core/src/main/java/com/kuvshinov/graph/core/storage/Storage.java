package com.kuvshinov.graph.core.storage;

import com.kuvshinov.graph.core.auth.User;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Kuvshinov
 */
public class Storage {

    private Path path;

    private String storageName;

    private Map<User, Grand> grands;

    public Storage(Path path) {
        this.path = path;
        grands = new HashMap<>();
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {

        this.storageName = storageName;
    }

    public void setUserGrand(User user, Grand grand) {
        grands.put(user, grand);
    }

    public Grand getUserGrand(User user) {
        return grands.get(user);
    }
}
