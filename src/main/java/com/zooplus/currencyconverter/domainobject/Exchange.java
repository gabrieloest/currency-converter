package com.zooplus.currencyconverter.domainobject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "exchange", schema = "zooplus")
public class Exchange {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	private Boolean deleted = false;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String currency;

	@Column(nullable = false, precision = 50, scale = 14)
	private BigDecimal amount;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exchange", cascade = { CascadeType.ALL })
	private List<Rate> rates;

	public Exchange() {
	}

	public Exchange(User user, String currency, BigDecimal amount, LocalDate date) {
		this.user = user;
		this.currency = currency;
		this.amount = amount;
		this.date = date;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

}
