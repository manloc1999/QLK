package entity;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "Supplier")
public class Supplier {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private int id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Email")
	private String email;

	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@Column(name = "Address")
	private String address;

	public Supplier() {
		super();
	}	
		
	@OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
	private Collection<Product> products;
	
	public Supplier(int id, String name, String email, String phoneNumber, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

/*	@OneToMany(mappedBy="supplier", fetch=FetchType.EAGER)
	private Collection<Product> products;*/


	

/*	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}*/

}
