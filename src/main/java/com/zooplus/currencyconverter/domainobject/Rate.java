package com.zooplus.currencyconverter.domainobject;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rate {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String currency;

	@Column(nullable = false, precision = 50, scale = 14)
	private BigDecimal amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_exchange")
	private Exchange exchange;

	public Rate() {
	}

	public Rate(Long id, String currency, BigDecimal amount, Exchange exchange) {
		this.id = id;
		this.currency = currency;
		this.amount = amount;
		this.exchange = exchange;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
