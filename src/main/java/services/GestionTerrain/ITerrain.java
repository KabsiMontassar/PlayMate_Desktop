package services.GestionTerrain;

import java.sql.SQLException;
public interface ITerrain<T> {

    void add(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(int id) throws SQLException;}