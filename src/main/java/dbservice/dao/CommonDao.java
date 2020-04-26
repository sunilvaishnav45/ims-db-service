package dbservice.dao;

import java.util.List;

public interface CommonDao<T> {

    public void save(T obj);

    public void delete(T obj);

    public void update(T obj);
}
