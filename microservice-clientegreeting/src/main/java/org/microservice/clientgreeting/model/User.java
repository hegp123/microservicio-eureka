
package org.microservice.clientgreeting.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Model class of controller
 * 
 * @author Roberto Crespo
 *
 */
@JsonRootName("user")
public class User implements Serializable {

    private static final long serialVersionUID = -7788619177798333712L;

    private String userId;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + "]";
    }

}
