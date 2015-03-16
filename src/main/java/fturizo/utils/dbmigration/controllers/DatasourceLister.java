package fturizo.utils.dbmigration.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

/**
 *
 * @author Fabio Andres
 */
@Named
@RequestScoped
public class DatasourceLister {
    
    private static final String JDBC_NAME_CONTEXT = "jdbc";
    
    public List<String> getRegisteredDatasourceNames(){
        List<String> dsNames = new ArrayList<>(); 
        try{
            InitialContext context = new InitialContext();
            NamingEnumeration<NameClassPair> results = context.list(JDBC_NAME_CONTEXT);
            while(results.hasMore()){
                NameClassPair pair = results.next();
                dsNames.add(JDBC_NAME_CONTEXT + "/" + pair.getName());
            }
        }catch(NamingException ex){
            Messages.addGlobalError("Got an error retrieving the JDBC datasources: {0}", ex.getMessage());
            if(Faces.isDevelopment()){
                ex.printStackTrace();
            }
        }
        return dsNames;
    }
}
