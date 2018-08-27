package com.zooplus.challenge.currencyconverter.domainobject;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(name = "email", columnNames = { "email" }))
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	@NotNull(message = "Email can not be null!")
	private String email;

	@Column(nullable = false)
	@NotNull(message = "Password can not be null!")
	private String password;

	@Column(nullable = false)
	private Boolean deleted = false;

	@Embedded
	private UserInformation contact = new UserInformation();

	@ManyToMany
	@JoinTable(name = "authorities", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_id", referencedColumnName = "id") })
	private Set<Authority> authorities = new HashSet<>();

	public User() {
	}

	public User(@NotNull(message = "Email can not be null!") String email,
			@NotNull(message = "Password can not be null!") String password) {
		this.email = email;
		this.password = password;
		this.deleted = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(ZonedDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public UserInformation getContact() {
		return contact;
	}

	public void setContact(UserInformation contact) {
		this.contact = contact;
	}

	@Override
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
