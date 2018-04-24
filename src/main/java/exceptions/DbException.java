package exceptions;

import java.sql.SQLException;

public class DbException extends Exception{

    private SQLException sqlException;

    public DbException(){
        super();
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(SQLException sqlException) {
        this.sqlException = sqlException;
    }

    public DbException(Throwable ex) {
        super(ex);
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}