package pg.share;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	  private static org.hibernate.SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
	public static SessionFactory getInstance() {
		return sessionFactory;
	}

	public static Session openSession() {
		return sessionFactory.openSession();
	}
    
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public static void close(){
		if (sessionFactory != null)
			sessionFactory.close();
		sessionFactory = null;
	
	}
}