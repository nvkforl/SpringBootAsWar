package com.springBootWar.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//https://github.com/KalpaD/spring-boot-stored-proc-jpa
@Entity
@Table(name = "employee")
@Data
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllEmployees",
                                    procedureName = "get_all_employees")//,   resultClasses = String.class)
})
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "first_name")
	private String first_name;

	@Column(name = "last_name")
	private String last_name;

	@Column(name = "start_date")
	private String start_date;

	@Column(name = "end_date")
	private String end_date;

	@Column(name = "salary")
	private String salary;

	@Column(name = "city")
	private String city;

	@Column(name = "description")
	private String description;
}
