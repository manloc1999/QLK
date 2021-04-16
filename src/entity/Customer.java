package entity;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "Customer")
public class Customer {

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

	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Collection<OutGoing> outgoing;

	public Customer() {
		super();
	}


	public Customer(int id, String name, String email, String phoneNumber, String address,
			Collection<OutGoing> outgoing) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.outgoing = outgoing;
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


	public Collection<OutGoing> getOutgoing() {
		return outgoing;
	}


	public void setOutgoing(Collection<OutGoing> outgoing) {
		this.outgoing = outgoing;
	}



	/*
	 * @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER) private
	 * Collection<OutGoing> ougoings;
	 */

	/*
	 * public Collection<OutGoing> getOugoings() { return ougoings; }
	 * 
	 * public void setOugoings(Collection<OutGoing> ougoings) { this.ougoings =
	 * ougoings; }
	 */
}
