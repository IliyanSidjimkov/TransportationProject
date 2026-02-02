package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "company_name", nullable = false, length = 100)
  private String companyName;



  @ManyToMany(mappedBy = "companies")
  @ToString.Exclude
  private Set<Employee> employees = new HashSet<>();

  @OneToMany(mappedBy = "company")
  @ToString.Exclude
  private List<Vehicle> vehicles = new ArrayList<>();

  @OneToMany(mappedBy = "company")
  @ToString.Exclude
  private List<Client> clients = new ArrayList<>();

  @OneToMany(mappedBy = "company")
  @ToString.Exclude
  private List<Shipment> shipments = new ArrayList<>();


}
