package com.thingword.alphonso.util;

import org.hibernate.SessionFactory;  
import org.hibernate.boot.registry.StandardServiceRegistry;  
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;  
import org.hibernate.cfg.Configuration;  
/**
 * Hibernate ��ʼ�����ù�����
 * @author waylau.com
 * 2014-3-23
 */  
public class HibernateUtil {  
     private static Configuration configuration;  
     private static SessionFactory sessionFactory;  
     private static StandardServiceRegistry standardServiceRegistry;  
        static {  
            try {  
             //��һ��:��ȡHibernate�������ļ�  hibernamte.cfg.xml�ļ�
              configuration = new Configuration().configure("hibernate.cfg.xml");          
             //�ڶ���:��������ע�ṹ��������ͨ�����ö����м������е�������Ϣ
              StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();  
              sb.applySettings(configuration.getProperties());  
             //����ע�����
              standardServiceRegistry = sb.build();    
            //������:�����Ự����
              sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);     
            } catch (Throwable ex) {  
                // Make sure you log the exception, as it might be swallowed
                System.err.println("Initial SessionFactory creation failed." + ex);  
                throw new ExceptionInInitializerError(ex);  
            }  
        }  

        public static SessionFactory getSessionFactory() {  
            return sessionFactory;  
        }  
}