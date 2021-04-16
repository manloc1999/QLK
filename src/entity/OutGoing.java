package entity;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "OutGoing")
public class OutGoing {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private int id;

	@Column(name = "Description")
	private String description;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "DD/mm/yyyy")
	private Date dateOut;

	@Column(name = "Amount")
	private int amount;
	
	@Column(name = "Date")
	private String date;

	@Column(name = "FreightCost")
	private double freightCost;

	@Column(name = "Discount")
	private double discount;
	
	@Column(name = "Total")
	private double total;

	@Column(name = "Status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "CustomerId")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "InComingId")
	private InComing incoming;

	public OutGoing() {
		super();
	}

	

	public OutGoing(int id, String description, Date dateOut, int amount, String date, double freightCost,
			double discount, double total, String status, Customer customer, InComing incoming) {
		super();
		this.id = id;
		this.description = description;
		this.dateOut = dateOut;
		this.amount = amount;
		this.date = date;
		this.freightCost = freightCost;
		this.discount = discount;
		this.total = total;
		this.status = status;
		this.customer = customer;
		this.incoming = incoming;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getFreightCost() {
		return freightCost;
	}

	public void setFreightCost(double freightCost) {
		this.freightCost = freightCost;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public InComing getIncoming() {
		return incoming;
	}

	public void setIncoming(InComing incoming) {
		this.incoming = incoming;
	}

}
