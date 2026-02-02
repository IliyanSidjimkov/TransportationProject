package org.example.dao;

import org.example.configurate.SessionFactoryUtil;
import org.example.entity.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VehicleDao {

  // CREATE
  public void create(Vehicle vehicle) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.persist(vehicle);
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  // UPDATE
  public void update(Vehicle vehicle) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.merge(vehicle);
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
      Vehicle vehicle = session.find(Vehicle.class, id);
      if (vehicle != null) {
        session.remove(vehicle);
      }
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }
}
