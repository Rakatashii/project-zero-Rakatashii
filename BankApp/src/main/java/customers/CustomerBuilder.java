package customers;

public class CustomerBuilder {
	private String firstName = null;
	private String lastName = null;
	private String telephone = null;
	private String email = null;
	private boolean isCitizen = false;
	private boolean isEmployed = false;
	private String employer = null;
	private String username, password; // Customers only
	private int id = -1; 

	public CustomerBuilder withID(int id) {
		this.id = id;
		return this;
	}
	public CustomerBuilder withUsername(String username) {
		this.username = username;
		return this;
	}
	public CustomerBuilder withPassword(String password) {
		this.password = password;
		return this;
	}
	public CustomerBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public CustomerBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public CustomerBuilder withTelephone(String telephone) {
		this.telephone = telephone;
		return this;
	}
	public CustomerBuilder withEmail(String email) {
		this.email = email;
		return this;
	}
	public CustomerBuilder withIsCitizen(boolean isCitizen) {
		this.isCitizen = isCitizen;
		return this;
	}
	public CustomerBuilder withIsEmployed(boolean isEmployed) {
		this.isEmployed = isEmployed;
		return this;
	}
	public CustomerBuilder withEmployer(String employer) {
		this.employer = employer;
		return this;
	}
	public UnverifiedCustomer makeUnverifiedCustomer() {
		UnverifiedCustomer newUnverifiedCustomer = new UnverifiedCustomer(firstName, lastName, telephone, email, isCitizen, isEmployed, employer);
		if (this.id != -1) newUnverifiedCustomer.setID(this.id);
		return newUnverifiedCustomer;
	}
	public Customer makeCustomer() {
		Customer newCustomer = new Customer(username, password, firstName, lastName, telephone, email, isCitizen, isEmployed, employer);
		if (this.id != -1) newCustomer.setID(this.id);
		return newCustomer;
	}
	public Customer makeCustomer(UnverifiedCustomer u) {
		Customer newCustomer = new Customer(username, password, u.firstName, u.lastName, u.telephone, u.email, u.isCitizen, u.isEmployed, u.employer);
		if (this.id != -1) newCustomer.setID(this.id);
		newCustomer.verified = true;
		return newCustomer;
	}
	
}