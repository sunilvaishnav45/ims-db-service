package dbservice.service.impl;

import dbservice.entity.User;
import dbservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public User getLoggedInUser() {
        //TODO : Update your logic here
        return null;
    }

    @Override
    public boolean userHasReadPermission(User user) {
        //TODO : Update your logic here
        return true;
    }

    @Override
    public boolean userHasWritePermission(User user) {
        //TODO : Update your logic here
        return true;
    }
}
