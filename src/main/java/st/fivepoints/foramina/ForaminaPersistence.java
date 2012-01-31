package st.fivepoints.foramina;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ForaminaPersistence {
  
  protected Connection db;
  protected String sqlite_file;
  
  public ForaminaPersistence(String sqlite_file) {
    try {
        Class.forName("org.sqlite.JDBC").newInstance();
        this.sqlite_file = Foramina.instance.getDataFolder() + File.separator + sqlite_file + ".sqlite";
        this.connect();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    
    if ( ! this.tablesExist() ) this.createTables();
  }

  protected final void connect() throws SQLException {
    Foramina.log("Connecting to sqlite database.");
    db = DriverManager.getConnection("jdbc:sqlite:" + this.sqlite_file);
  }
  
  protected void disconnect() {
    try {
      db.close();
    } catch (SQLException e) {
      Foramina.log("Error while disconnecting from database: " + e.getMessage());
    } finally {
      db = null;
    }
  }
  
  private boolean tablesExist() {
    ResultSet rs = this.query("SELECT count(*) as count FROM sqlite_master WHERE type = \"table\" AND name IN (\"locations\", \"scaenus\")");
    try {
      while ( rs.next() ) {
        if ( rs.getInt("count") == 2 ) return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  private void createTables() {
    Foramina.log("Creating sqlite database.");
    this.execute("CREATE TABLE locations ( id integer primary key, world_uid text not null, x real not null, y real not null, z real not null)");
    this.execute("CREATE UNIQUE INDEX idx_locations on locations ( world_uid, x, y, z )");
    this.execute("CREATE TABLE scaenus (location_id integer, slot integer, type_id integer, data integer)");
    this.execute("CREATE UNIQUE INDEX idx_scaenus on scaenus ( location_id, slot )");
  }
  
  public int insertLocation(String world_uid, double x, double y, double z) {
    String sql = "INSERT INTO locations (world_uid, x, y, z) VALUES (?, ?, ?, ?)";
    PreparedStatement st = null;
    ResultSet generatedKeys = null;
    int id = 0;
    
    try {
      st = this.db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      st.setString(1, world_uid);
      st.setDouble(2, x);
      st.setDouble(3, y);
      st.setDouble(4, z);
      
      int affectedRows = st.executeUpdate();
      if (affectedRows == 0) {
          throw new SQLException("Creating location record, no rows affected.");
      }

      generatedKeys = st.getGeneratedKeys();
      if (generatedKeys.next()) {
          id = generatedKeys.getInt(1);
      } else {
          throw new SQLException("Could not retrieve location_id.");
      }
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
    return id;
  }

  public void insertScaena(int location_id, int slot, int typeID, byte data ) {
    String sql = "INSERT INTO scaenus (location_id, slot, type_id, data) VALUES (?, ?, ?, ?)";
    PreparedStatement st = null;

    try {
      st = this.db.prepareStatement(sql);
      st.setInt  (1, location_id);
      st.setInt  (2, slot);
      st.setInt  (3, typeID);
      st.setByte (4, data);
      
      int affectedRows = st.executeUpdate();
      if (affectedRows == 0) {
          throw new SQLException("Creating scaena, no rows affected.");
      }

    } catch ( SQLException e ) {
      e.printStackTrace();
    }
  }
  
  public ResultSet query(String sql) {
    ResultSet rs = null;
    try {
      Statement st = this.db.createStatement();
      rs = st.executeQuery(sql);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rs;
  }

  public void execute(String sql) {
    try {
      Statement st = this.db.createStatement();
      st.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
  }
  
}
 