package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "position", nullable = false)
  private String position;

  @Column(name = "salary")
  private Double salary;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @ToString.Exclude
  @JoinTable(
    name = "company_employee",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "company_id")
  )
  private Set<Company> companies =  new HashSet<>();

  @OneToMany(mappedBy = "driver")
  @ToString.Exclude
  private List<Vehicle> vehicles = new ArrayList<>();
}
