package entity;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private int id;

	@Column(name = "Name")
	private String name;
	
	@Column(name = "Photo")
	private String photo;

	@Column(name = "Sellingprice")
	private float sellingPrice;

	@ManyToOne
	@JoinColumn(name = "SupplierId")
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private Collection<InComing> incoming;

	public Product() {
		super();
	}


	public Product(int id, String name, String photo, float sellingPrice, Supplier supplier,
			Category category, Collection<InComing> incoming) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.sellingPrice = sellingPrice;
		this.supplier = supplier;
		this.category = category;
		this.incoming = incoming;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
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

	public float getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Collection<InComing> getIncoming() {
		return incoming;
	}

	public void setIncoming(Collection<InComing> incoming) {
		this.incoming = incoming;
	}

}
