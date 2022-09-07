public class Customer {
    private int customerID;
    private String phoneNumber;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String paymentMethod;
    private String address;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getAddress() {
        return address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAddress(String address) {
        this.address = address;
    }
//    public Customer(String customerID, String firstName, String lastName, String phoneNumber) {
//        this.customerID = customerID;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
