package st.fivepoints.foramina;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ForaminaPersistence {
  
  protected Connection db;
  protected String sqlite_file;
  
  private Foramina plugin;
  private String table = new String("inventories");
  
  Logger log = Logger.getLogger("Minecraft");
  
  public ForaminaPersistence(Foramina plugin, String sqlite_file) {
    this.plugin = plugin;
    try {
        Class.forName("org.sqlite.JDBC").newInstance();
        this.sqlite_file = this.plugin.getDataFolder() + File.separator + sqlite_file + ".sqlite";
        this.connect();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    
    if ( ! this.tableExists() ) this.createTable();
  }

  protected final void connect() throws SQLException {
    log.info("[Foramina] Connecting to sqlite database \"" + this.sqlite_file + "\".");
    db = DriverManager.getConnection("jdbc:sqlite:" + this.sqlite_file);
  }
  
  protected void disconnect() {
    try {
      db.close();
    } catch (SQLException e) {
      log.log(Level.WARNING, "Error while disconnecting from database: {0}", e.getMessage());
    } finally {
      db = null;
    }
  }
  
  private boolean tableExists() {
    ResultSet rs = this.query("SELECT name FROM sqlite_master WHERE type = \"table\"");
    try {
      while ( rs.next() ) {
        if ( rs.getString("name").equals(this.table) ) return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  private void createTable() {
    log.info("[Foramina] Creating sqlite database.");
    this.execute("CREATE TABLE " + this.table + " ( world_uid text not null, x real not null, y real not null, z real not null, slot integer, type_id integer, quantity integer, durability integer)");
    this.execute("CREATE UNIQUE INDEX idx_" + this.table + " on " + this.table + " ( world_uid, x, y, z, slot )");
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
 