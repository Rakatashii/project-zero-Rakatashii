package customers;

import DAO.CustomerDAO;
import accounts.Account;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import model.CustomerContainer;
import people.Person;
import utility.Helpers;

public class Customer extends UnverifiedCustomer{
	private CustomerDAO customerDAO = new CustomerDAO();
	
	private String username, password;
	private String firstName, lastName;
	private String telephone, email;
	private boolean isCitizen, isEmployed;
	private String employer;
	protected boolean verified = true;
	private boolean accountsAreFlagged = false;
	
	// TODO private static int numCustomers = customerDAO.getNumCustomersInDB();
	private static int numCustomers = 0;
	private int custID = numCustomers;
	
	static CustomerContainer customerContainer;
	static boolean customerContainerIsSet = false;
	
	SavingsAccount savingsAccount = null;
	CheckingAccount checkingAccount = null;
	
	public Customer() { 
		super();
		custID = -1;
	}
	public Customer(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		custID = numCustomers;
		++numCustomers;
		++numTotalCustomers;
		++numPeople;
		if (customerContainerIsSet) customerContainer.push(this);
		makeNewAccounts();
	}
	public Customer(String username, String password, String firstName, String lastName, String telephone, String email, boolean citizen, boolean employed, String employer) {
		super(firstName, lastName, telephone, email, citizen, employed, employer);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.isCitizen = citizen;
		this.isEmployed = employed;
		this.employer = employer;
		custID = numCustomers;
		++numCustomers;
		++numTotalCustomers;
		++numPeople;
		--numUnverifiedCustomers;
		if (customerContainerIsSet) customerContainer.push(this);
		makeNewAccounts();
	}
	public static void passCustomerContainer(CustomerContainer customers) {
		customerContainer = customers;
		customerContainerIsSet = true;
	}
	@Override
	public int getID() {
		return custID;
	}
	@Override
	public String getUsername()    { return this.username; }
	@Override
	public String getPassword()    { return this.password; }
	public String getFirstname()   { return this.firstName; }
	public String getLastname()    { return this.lastName; }
	public String getTelephone()   { return this.telephone; }
	public String getEmail() 	   { return this.email; }
	public boolean getIsCitizen()  { return this.isCitizen; }
	public boolean getIsEmployed() { return this.isEmployed; }
	public String getEmployer()    { return this.employer; }
	@Override
	public void setID(int id) {
		custID = id;
	}
	@Override
	public int getCount() {
		return numCustomers;
	}
	@Override
	public void setCount(int count) {
		numCustomers = count;
	}
	@Override
	public void getInfo() {
		System.out.println("ID: " + this.custID);
		if (this.username != null) System.out.println("Username: " + this.username);
		if (this.password != null) System.out.println("Password: " + this.password);
		if (this.firstName != null) System.out.println("First name: " + this.firstName);
		if (this.lastName != null) System.out.println("First name: " + this.lastName);
		if (telephone != null) System.out.println("Telephone: " + firstName);
		if (email != null) System.out.println("email: " + email);
		if (isCitizen)
			System.out.println("US Citizen: true");
		else 
			System.out.println("US Citizen: false");

		if (isEmployed) {
			System.out.println("Currently employed: true");
			if (employer != null)
				System.out.println("Employer: " + employer);
		}
		else 
			System.out.println("Currently employed: false");
	}
	@Override
	public void printRow() {
		Helpers helper = new Helpers();
		String citizen = helper.boolToString(this.isCitizen);
		String employed = helper.boolToString(this.isEmployed);
		System.out.printf(String.format("%-4d%-20s%-20s%-15s%-15s%-14s%-40s%-10s%-10s%-35s\n", this.getID(), this.username, this.password, this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer));
		// boolToInt (0/1) System.out.printf(String.format("%-4d%-20s%-20s%-15s%-15s%-14s%-40s%-10d%-10d%-35s\n", this.getID(), this.username, this.password, this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer));
	}
	@Override
	public String getRow() {
		Helpers helper = new Helpers();
		String citizen = helper.boolToString(this.isCitizen);
		String employed = helper.boolToString(this.isEmployed);
		return String.format("%-4d%-20s%-20s%-15s%-15s%-14s%-40s%-10s%-10s%-35s\n", this.getID(), this.username, this.password, this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
		// boolToInt (0/1) return String.format("%-4d%-20s%-20s%-15s%-15s%-14s%-40s%-10d%-10d%-35s\n", this.getID(), this.username, this.password, this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	
	public void setSavingsAccount(SavingsAccount savings) {
		savingsAccount = savings;
		if (checkingAccount != null) {
			if (checkingAccount.isJoint()) savingsAccount.setJointCustomer(checkingAccount.getJointCustomer());
		}
	}
	public SavingsAccount getSavingsAccount() {
		return savingsAccount;
	}
	public void setCheckingAccount(CheckingAccount checking) {
		checkingAccount = checking;
		if (savingsAccount != null) {
			if (savingsAccount.isJoint()) savingsAccount.setJointCustomer(savingsAccount.getJointCustomer());
		}
	}
	public CheckingAccount getCheckingAccount() {
		return checkingAccount;
	}
	public boolean hasSavingsAccount() {
		if (savingsAccount != null) return true;
		return false;
	}
	public boolean hasCheckingAccount() {
		if (savingsAccount != null) return true;
		return false;
	}
	public void makeNewAccounts() {
		this.savingsAccount = new SavingsAccount();
		this.checkingAccount = new CheckingAccount();
		this.savingsAccount.setPairedAccount(this.checkingAccount);
		this.checkingAccount.setPairedAccount(this.savingsAccount);
	}
	public void flagAccounts(boolean flag) {
		this.savingsAccount.flag();
		this.checkingAccount.flag();
	}
}
