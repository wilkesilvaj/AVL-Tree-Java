package avlTree;

public class Account {
	public int accountNumber;
	public String firstName;
	public String lastName;
	public double amount;
	public String address;
	public String huffmanAddress;
	
	public Account(String newData) {
		String[] data = newData.split(",");
		accountNumber = Integer.parseInt(data[0]);
		firstName = data[1];
		lastName = data[2];
		amount = Double.parseDouble(data[3]);
		address = data[4];
	}
	
	public Account (int newAccount, String newFirstName, String newLastName, double newAmount, String newAddress) {
		accountNumber = newAccount;
		firstName = newFirstName;
		lastName = newLastName;
		amount = newAmount;
		address = newAddress;
	}
	

}
