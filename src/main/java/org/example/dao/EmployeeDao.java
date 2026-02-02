package org.example.dao;

import org.example.configurate.SessionFactoryUtil;
import org.example.entity.Company;
import org.example.entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDao {

  // CREATE
  public void create(Employee employee) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.persist(employee);
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  // UPDATE
  public void update(Employee employee) {
    Transaction tx = null;
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();
      session.merge(employee);
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
      Employee employee = session.find(Employee.class, id);
      if (employee != null) {
        session.remove(employee);
      }
      tx.commit();

    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }
//Намиране по id
  public Employee findById(Long id) {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.find(Employee.class, id);
    }
  }
  //назначаване на работа
  public void hireEmployee(Long employeeId, Long companyId) {
    Transaction tx = null;

    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      tx = session.beginTransaction();

      Employee employee = session.find(Employee.class, employeeId);
      Company company = session.find(Company.class, companyId);

      employee.getCompanies().add(company);
      company.getEmployees().add(employee);

      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      throw e;
    }
  }

  //Служители по квалификация
  public List<Employee> findByPosition(String position) {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery(
          "FROM Employee e WHERE e.position = :pos",
          Employee.class
        )
        .setParameter("pos", position)
        .list();
    }
  }
  //Служители по заплата
  public List<Employee> findAllOrderBySalaryDesc() {
    try (Session session = SessionFactoryUtil
      .getSessionFactory()
      .openSession()) {

      return session.createQuery(
        "FROM Employee e ORDER BY e.salary DESC",
        Employee.class
      ).list();
    }
  }
}
