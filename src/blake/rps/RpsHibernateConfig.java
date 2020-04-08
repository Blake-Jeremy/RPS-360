package blake.rps;
/*******************************************************************
 *  RpsHibernateConfig class
 *  Description: This is where I configure Hibernate.
 *******************************************************************/

// Imported Libraries
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class RpsHibernateConfig {

    private SessionFactory sessionFactory;

    public RpsHibernateConfig() {
        Configuration config = new Configuration();
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        config.setProperty("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver");
        //change the next line of code to match your MySQL URL and port
        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/history");
        //change the next two lines of code to match your MySQL user name and password.
        config.setProperty("hibernate.connection.username", "root");
        config.setProperty("hibernate.connection.password", ";OfOKA_@p&C[");
        config.setProperty("hibernate.connection.pool_size", "10");
        config.setProperty("hibernate.connection.autocommit", "true");
        config.setProperty("hibernate.id.new_generator_mappings", "false");
        config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        // un-comment to be able to drop and recreate tables for your data classes listed below
        //config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        config.setProperty("hibernate.transaction.coordinator_class", "jdbc");
        config.setProperty("hibernate.current_session_context_class", "thread");
        // Additional configuration items for visible hibernate console output
        config.setProperty("hibernate.show_sql", "false");
        config.setProperty("hibernate.generate_statistics", "false");
        config.setProperty("hibernate.use_sql_comments", "false");
        // Add your classes here that you want to match your database tables
        //config.addAnnotatedClass(User.class);
        //config.addAnnotatedClass(PhoneNumber.class);
        config.addAnnotatedClass(RpsHibernateJsonEntry.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
    }

    public  Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
