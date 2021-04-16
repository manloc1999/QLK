package entity;

import javax.persistence.*;

@Entity
@Table(name = "Admin")
public class Admin{
	
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private int id;	
	
	@Column(name = "Address")
	private String address;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "Username")
	private String username;
	
	@Column(name = "FirstName")
	private String firstname;
	
	@Column(name = "Lastname")
	private String lastname;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "ImagePath")
	private String imagePath;

	public Admin() {
		super();
	}

	public Admin(int id, String address, String password, String username, String firstname, String lastname,
			String email, String imagePath) {
		super();
		this.id = id;
		this.address = address;
		this.password = password;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.imagePath = imagePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}	
}
