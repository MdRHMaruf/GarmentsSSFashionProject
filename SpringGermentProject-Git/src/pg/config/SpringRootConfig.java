/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

   //@Configuration      
   //@ComponentScan(basePackages = {"pg"})
public class SpringRootConfig {
    //TODO Serice,Dao,datasource ,Email Sendar or some other busniess layer beans.
       
       @Bean
       public BasicDataSource getDataSource(){
           BasicDataSource bds=new BasicDataSource();
           bds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           bds.setUrl("jdbc:sqlserver://192.168.0.110:1433;databaseName =SSFashionSpringProject");
           bds.setUsername("sa");
           bds.setPassword("Cursor777");
           bds.setMaxTotal(2);
           bds.setInitialSize(1);
           bds.setTestOnBorrow(true);
           bds.setValidationQuery("SELECT 1");
           bds.setDefaultAutoCommit(true);
           return bds;
       }
       
}
