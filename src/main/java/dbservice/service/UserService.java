package dbservice.service;

import dbservice.entity.User;

public interface UserService {

    public boolean userHasReadPermission(User user);

    public boolean userHasWritePermission(User user);

}
