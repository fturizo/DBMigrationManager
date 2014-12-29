package fturizo.utils.dbmigration.controllers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Fabio Andres
 */
@Named
@SessionScoped
public class DataSourceReferencer implements Serializable{
    
    private String currentDSName;

    public String getCurrentDSName() {
        return currentDSName;
    }

    public void setCurrentDSName(String currentDSName) {
        this.currentDSName = currentDSName;
    }
    
    @Produces
    public DataSource getCurrentDataSource(){
        if(currentDSName == null){
            return null;
        }else{
            InitialContext context;
            try {
                context = new InitialContext();
                return (DataSource)context.lookup(currentDSName);
            } catch (NamingException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
