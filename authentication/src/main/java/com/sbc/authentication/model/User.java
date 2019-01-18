package com.sbc.authentication.model;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sbc.authentication.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "last_name", length = 50)
	private String lastName;

	@Column(name = "username", unique = true, length = 50)
	private String username;

	@Column(name = "password", length = 50)
	@Lob
	private String password;

	@Column(name = "email", unique = true, length = 100)
	private String email;
	
	@Column(name = "active")
	private int active = 1;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	public User(String name, String lastName, String username, String password, String email) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
	}


	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public boolean hasRole(String roleName) {
		
		for(Role role : this.roles) {
			if(role.getRole().equals(roleName))
				return true;
		}
		return false;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
	    this.password = passwordEncoder.encode(this.password);
	}
}
