package main;

import java.util.ArrayList;

public interface Repository<T extends Model> {
    ArrayList<T> find(String column, String[] conditions, boolean joinTable, String joinTableName, Connection connection);

    T findOne(String column, String[] conditions, boolean joinTable, String joinTableName, Connection connection);

    T insert(String[] data, Connection connection);
}
