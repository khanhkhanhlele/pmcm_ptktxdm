package hust.project.base.utils.sql_hikari;

public class DatabaseManager {
    HikariClient defaultClient;
    SQLJavaBridge defaultBridge;
    private static DatabaseManager ins = null;

    private DatabaseManager() {

    }

    public static DatabaseManager instance() {
        if (ins == null) {
            ins = new DatabaseManager();
        }
        return ins;
    }
    public void init() {
        String jdbcURL = "jdbc:mysql://localhost:3306/pmchamcong";
        String user = "root";
        String password = "123456";
        String database = "pmchamcong";
        HikariClient sqlClient = new HikariClient(jdbcURL, user, password, database);
        SQLJavaBridge bridge= new SQLJavaBridge(sqlClient);
        this.defaultClient = sqlClient;
        this.defaultBridge = bridge;
    }
    public SQLJavaBridge defaulSQLJavaBridge() {
        return defaultBridge;
    }

}
