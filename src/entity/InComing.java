package entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "InComing")
public class InComing {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private int id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Date")
	private String date;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateIn;

	@Column(name = "Amount")
	private int amount;

	@Column(name = "Out")
	private int out;

	@Column(name = "Also")
	private int also;

	@Column(name = "Price")
	private double price;

	@Column(name = "Description")
	private String description;

	@Column(name = "Status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "ProductId")
	private Product product;

	@OneToMany(mappedBy = "incoming", fetch = FetchType.EAGER)
	private Collection<OutGoing> outgoing;

	private double priceEach;

	public InComing() {
		super();
	}

	public InComing(int id, String name, String date, Date dateIn, int amount, int out, int also, double price,
			String description, String status, Product product, Collection<OutGoing> outgoing, double priceEach) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.dateIn = dateIn;
		this.amount = amount;
		this.out = out;
		this.also = also;
		this.price = price;
		this.description = description;
		this.status = status;
		this.product = product;
		this.outgoing = outgoing;
		this.priceEach = priceEach;
	}

	public int getAlso() {
		return also;
	}

	public void setAlso(int also) {
		this.also = also;
	}

	public int getOut() {
		return out;
	}

	public void setOut(int out) {
		this.out = out;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getPriceEach() {
		return priceEach;
	}

	public void setPriceEach(double priceEach) {
		this.priceEach = priceEach;
	}

	public Collection<OutGoing> getOutgoing() {
		return outgoing;
	}

	public void setOutgoing(Collection<OutGoing> outgoing) {
		this.outgoing = outgoing;
	}
}
