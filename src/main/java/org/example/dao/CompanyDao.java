package org.example.dao;

import org.example.configurate.SessionFactoryUtil;
import org.example.entity.Company;
import org.example.entity.PaymentStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompanyDao {

  // CREATE – въвеждане
  public void create(Company company) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.persist(company);
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  // UPDATE – редактиране
  public void update(Company company) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.merge(company);
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  // DELETE – изтриване
  public void delete(Long id) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      Company company = session.find(Company.class, id);
      if (company != null) {
        session.remove(company);
      }
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }
  public Company findById(Long id) {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.find(Company.class, id);
    }
  }

  //Компании по име
  public List<Company> findAllOrderByName() {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery(
        "FROM Company c ORDER BY c.companyName",
        Company.class
      ).list();
    }
  }

  //Компании по приходи
  public List<Object[]> findCompaniesByRevenue() {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery("""
            SELECT c.companyName, SUM(s.price)
            FROM Company c
            JOIN Shipment s ON s.company = c
            WHERE s.paymentStatus = :status
            GROUP BY c.companyName
            ORDER BY SUM(s.price) DESC
        """, Object[].class)
        .setParameter("status", PaymentStatus.PAID)
        .list();
    }
  }
}
