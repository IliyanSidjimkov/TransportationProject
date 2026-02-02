package org.example.configurate;


import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

            private static SessionFactory sessionFactory;

            public static SessionFactory getSessionFactory() {
                if (sessionFactory == null) {
                    Configuration configuration = new Configuration();
                    configuration.addAnnotatedClass(Company.class);
                  configuration.addAnnotatedClass(Client.class);
                  configuration.addAnnotatedClass(Vehicle.class);
                  configuration.addAnnotatedClass(Employee.class);
                  configuration.addAnnotatedClass(Shipment.class);

                    ServiceRegistry serviceRegistry
                            = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();
                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                }
                return sessionFactory;
            }


}
