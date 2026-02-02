package org.example.dao;

import org.example.configurate.SessionFactoryUtil;
import org.example.entity.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClientDao {

  // CREATE
  public void create(Client client) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.persist(client);
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  // UPDATE
  public void update(Client client) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.merge(client);
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  // DELETE
  public void delete(Long id) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      Client client = session.find(Client.class, id);
      if (client != null) {
        session.remove(client);
      }
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }
}
