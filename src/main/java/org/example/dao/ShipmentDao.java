package org.example.dao;

import org.example.configurate.SessionFactoryUtil;
import org.example.entity.Company;
import org.example.entity.PaymentStatus;
import org.example.entity.Shipment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ShipmentDao {

  // CREATE
  public void create(Shipment shipment) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.persist(shipment);
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  // UPDATE
  public void update(Shipment shipment) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.merge(shipment);
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
      Shipment shipment = session.find(Shipment.class, id);
      if (shipment != null) {
        session.remove(shipment);
      }
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  //За превозите по дестинация
  public List<Shipment> findByDestination(String destination) {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery(
          "FROM Shipment s WHERE s.destination LIKE :dest",
          Shipment.class
        )
        .setParameter("dest", "%" + destination + "%")
        .list();
    }
  }

  //Намиране на всички доставки
  public List<Shipment> findAll() {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery(
        "FROM Shipment",
        Shipment.class
      ).list();
    }
  }

  //Общ брой извършени превози
  public Long getTotalShipmentsCount() {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery(
        "SELECT COUNT(s) FROM Shipment s",
        Long.class
      ).getSingleResult();
    }
  }

  //Обща сума на извършените платени превози
  public Double getTotalRevenue() {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery(
          "SELECT SUM(s.price) FROM Shipment s WHERE s.paymentStatus = :status",
          Double.class
        )
        .setParameter("status", PaymentStatus.PAID)
        .getSingleResult();
    }
  }
  //Превози по шофьор
  public List<Object[]> getShipmentsCountPerDriver() {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery("""
            SELECT e.firstName, e.lastName, COUNT(s)
            FROM Shipment s
            JOIN s.vehicle v
            JOIN v.driver e
            GROUP BY e.id, e.firstName, e.lastName
        """, Object[].class).list();
    }
  }
  //приходи на компания за определен период
  public Double getCompanyRevenueForPeriod(
    Company company,
    LocalDate from,
    LocalDate to
  ) {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery("""
            SELECT SUM(s.price)
            FROM Shipment s
            WHERE s.company = :company
              AND s.paymentStatus = :status
              AND s.shipmentDate BETWEEN :from AND :to
        """, Double.class)
        .setParameter("company", company)
        .setParameter("status", PaymentStatus.PAID)
        .setParameter("from", from)
        .setParameter("to", to)
        .getSingleResult();
    }
  }
  //приход от всеки шофьор
  public List<Object[]> getRevenuePerDriver() {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery("""
            SELECT e.firstName, e.lastName, SUM(s.price)
            FROM Shipment s
            JOIN s.vehicle v
            JOIN v.driver e
            WHERE s.paymentStatus = :status
            GROUP BY e.id, e.firstName, e.lastName
        """, Object[].class)
        .setParameter("status", PaymentStatus.PAID)
        .list();
    }
  }

}
