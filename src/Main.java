import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

//USER

class User {
    private  String  Namee , Address ,Mobile;
    private int  Pin ;
    private double  UserIdd ;
    protected double balance;

    public String getNamee() {
        return Namee;
    }
    public String getAddress() {
        return Address;
    }
    public String getMobile() {return Mobile;}
    public int getPin() {
        return Pin;
    }
    public double getUserId() {
        return UserIdd;
    }

    
    public void setNamee(String namee) {
        Namee = namee;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public void setMobile(String mobile) {Mobile = mobile;}
    public void setPin(int pin) {
        Pin = pin;
    }
    public void setUserId(double userIdd) {
        UserIdd = userIdd;
    }
    
    
    public double getBalance() {
        return balance;
    }
    
}

class Account extends User {
    static ArrayList<String> RechargedContacts=new ArrayList<>();
    static ArrayList<String> MoneyTransferedContacts=new ArrayList<>();
    static Scanner input=Main.input;
    static HashMap<String, String> ContactList = new HashMap<String, String>();

    public void addRechargedContacts(String mobilenum){
        int count=0;
        for(int i=0;i<RechargedContacts.size();i++){
            if(!mobilenum.equals(RechargedContacts.get(i))){
                count++;
            }
            else{
                break;
            }
        }
        if(count==RechargedContacts.size()){
            RechargedContacts.add(mobilenum);
        }

    }

    public void addMoney() {
        System.out.print("\nEnter the amount to deposit:");
        double amount1=input.nextDouble();
        balance = balance + amount1;

        System.out.println("\nThe amount '"+amount1+"' added successfully to your account :) \n");
        System.out.print("\nPress 'c' to continue.....   ");
        String enter=input.next();
        Main.HomePage();
    }
    
    public void sendMoney() {
        int count1=0;
        int count=0;
        if(MoneyTransferedContacts.size()>=0){
            for (String i : MoneyTransferedContacts) {
                if(count1==0) {
                    System.out.println("\nContacts of previous money transaction :");
                }
                System.out.println(count1 + 1 + " .  " + MoneyTransferedContacts.get(count1));
                count1++;
            }
            if(ContactList.size()>0) {
                System.out.println("\nYour saved contacts :\n");
                Iterator ContactListIterator = ContactList.entrySet().iterator();
                while (ContactListIterator.hasNext()) {

                    Map.Entry mapElement = (Map.Entry) ContactListIterator.next();
                    ++count;
                    System.out.println(count + " . " + mapElement.getKey() + " : " + mapElement.getValue());
                }
            }
        }
        System.out.print("\nEnter the mobile number to send money : ");
        String sendmobilenum=input.next();
        System.out.print("\nEnter the amount to send :");
        int sendmoney=input.nextInt();
        if(balance>sendmoney &&  sendmobilenum.matches("[987][0-9]{9}")) {
            MoneyTransferedContacts.add(sendmobilenum);
            System.out.println("\nSuccessfully transfered the amount '"+sendmoney+"' to the mobile number '"+sendmobilenum+"'\n");
            balance=balance-sendmoney;
            System.out.print("\nPress 'c' to continue.....   ");
            String enter=input.next();
            Main.HomePage();
        }
        else{
            System.out.println("Invalid try again\n\nSolution :  Check your Account balance or Enter a valid mobile number");
            System.out.print("\nPress 'c' to continue.....   ");
            String enter=input.next();
            Main.HomePage();
        }
    }

    public void mobileRecharge() {
        int count2 = 0;
        int count=0;
        if(RechargedContacts.size()>=0){
            for (String i : RechargedContacts) {
                if(count2==0){
                    System.out.println("\nContacts of previous mobile recharge :\n");
                }
                System.out.println(count2 + 1 + " .  " + RechargedContacts.get(count2));
                count2++;
            }
            if(ContactList.size()>=1) {
                System.out.println("\nYour saved contacts :\n");
                Iterator ContactListIterator = ContactList.entrySet().iterator();
                while (ContactListIterator.hasNext()) {

                    Map.Entry mapElement = (Map.Entry) ContactListIterator.next();
                    ++count;
                    System.out.println(count + " . " + mapElement.getKey() + " : " + mapElement.getValue());
                }
            }
        }
        System.out.print("\nPlans available\n");
        System.out.print("\n1. 182 plan(voice-N/A,internet-available)\n2. 299 plan(voice,internet-available)\n3. 399 plan(voice,internet-available) \n");
        System.out.print("\nEnter the mobile number to recharge : ");
        String mobilenum=input.next();
        System.out.print("\nEnter the plan : ");
        int plan=input.nextInt();
        switch(plan){
            case 1:

                if(balance>182 &&  mobilenum.matches("[987][0-9]{9}")) {
                    balance=balance-182;
                    addRechargedContacts(mobilenum);
                    System.out.println("\nRecharge of '182.0' is successful for the number '" + mobilenum + "'\nBenefits:Unlimited Data,no Voice/SMS\n");
                    System.out.print("\nPress 'c' to continue.....   ");
                    String enter=input.next();
                    break;
                }
                else{
                    System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance or Enter a valid mobile number\n");
                    System.out.print("\nPress 'c' to continue.....   ");
                    String enter=input.next();
                    Main.HomePage();
                }
            case 2:

                if(balance>182 &&  mobilenum.matches("[987][0-9]{9}")) {
                    balance=balance-299;
                    RechargedContacts.add(mobilenum);
                    System.out.println("\nRecharge of '299.0' is successful for the number '" + mobilenum + "\nBenefits:Unlimited Data,no Voice/SMS\n");
                    System.out.print("\nPress 'c' to continue.....   ");
                    String enter=input.next();
                    break;
                }
                else{
                    System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance or Enter a valid mobile number\n");
                    System.out.print("\nPress 'c' to continue.....   ");
                    String enter=input.next();
                    Main.HomePage();
                }
            case 3:

                if(balance>182 &&  mobilenum.matches("[987][0-9]{9}")) {
                    balance=balance-399;
                    RechargedContacts.add(mobilenum);
                    System.out.println("\nRecharge of '399.0' is successful for the number '" + mobilenum + "'\nBenefits:Unlimited Data,no Voice/SMS\n");
                    System.out.print("\nPress 'c' to continue.....   ");
                    String enter=input.next();
                    break;
                }
                else{
                    System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance or Enter a valid mobile number\n");
                    System.out.print("\nPress 'c' to continue.....   \n");
                    String enter=input.next();
                    Main.HomePage();
                }
        }
        Main.HomePage();
    }

    public void DTHRecharge() {
        input.nextLine();
        System.out.print("\nEnter the Customer id : ");
        String customerId=input.next();
        System.out.print("\nEnter the amount to recharge : ");
        int DTHAmount=input.nextInt();
        if(balance>DTHAmount) {
            balance = balance - DTHAmount;
            System.out.println("\nSuccessfully recharged for amount '" + DTHAmount + "' :)\n");
            System.out.print("\nPress 'c' to continue.....   ");
            String enter = input.next();
            Main.HomePage();
        }
        else{
            System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance\n");
            System.out.print("\nPress 'c' to continue.....   ");
            String enter=input.next();
            Main.HomePage();
        }
    }

    public void electricity() {
        input.nextLine();
        System.out.print("\nEnter the State : ");
        String State=input.nextLine();
        System.out.print("\nEnter the customerId : ");
        String customerId=input.next();
        System.out.print("\nEnter the amount to pay electricity bill:");
        int ElectricityAmount=input.nextInt();
        if(balance>ElectricityAmount) {
            balance = balance - ElectricityAmount;
            System.out.println("\nSuccessfully paid the amount '" + ElectricityAmount + "' :)\n");
            System.out.print("\nPress 'c' to continue.....   ");
            String enter = input.next();
            Main.HomePage();
        }
        else{
            System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance\n");
            System.out.print("\nPress 'c' to continue.....   ");
            String enter=input.next();
            Main.HomePage();
        }

    }

    public void checkBalance() {
        System.out.println("\nBalance is : "+balance);
        System.out.print("\nPress 'c' to continue.....   ");
        String enter=input.next();
        Main.HomePage();
    }



}

//MAIN


public class Main {
    static Scanner input = new Scanner(System.in);
    static Account acc = new Account();
    static Contacts con=new Contacts();
    static List<User> Users = new ArrayList<>();
    static boolean UserAuth = false;
    static User CurrentUser;

    public static void main(String[] args) {
        mainfunction();
    }

    public static void mainfunction() {
        System.out.println("\n\nWelcome to Payteam\n\n1.Login\n2.Signup\n3.Exit\n\n ");
        System.out.print("\nEnter your input : ");
        int maininput = input.nextInt();
        switch (maininput) {
            case 1:
                Login();
                break;
            case 2:
                Signup();
                break;
            default:
                Exit();
                break;
        }
    }

    public static void Login() {
        System.out.println("\n\n");
        System.out.print("UserId : ");
        int useridinput = input.nextInt();
        System.out.print("Pin : ");
        int pininput = input.nextInt();
        for (User ele : Users
        ) {
            if (ele.getUserId() == useridinput && ele.getPin()== pininput) {
                UserAuth = true;
                CurrentUser = ele;
                System.out.println("\nLogin successful  :)\n");
                System.out.print("\nPress 'c' to continue.....   ");
                String enter=input.next();
                HomePage();
                break;
            }
        }
        System.out.println("\nInvalid Try again\n");
        System.out.print("\nPress 'c' to continue.....   ");
        String enter=input.next();
        mainfunction();
    }

    public static void Signup() {
        System.out.println("\n");
        input.nextLine();
        System.out.print("UserName : ");
        String usernameinput = input.nextLine();
        System.out.print("Address : ");
        String addressinput = input.nextLine();
        System.out.print("Mobile : ");
        String mobileinput = input.next();
        System.out.print("Pin : ");
        int pininput = input.nextInt();
        int useridd = Users.size() + 1;
        User NewUser = new User();
        NewUser.setNamee(usernameinput);
        NewUser.setAddress(addressinput);
        NewUser.setMobile(mobileinput);
        NewUser.setPin(pininput);
        NewUser.setUserId(useridd);
        System.out.println("\nYour userid is :" + useridd);
        Users.add(NewUser);
        System.out.println("\nAccount Created successful :)\n");
        System.out.print("\nPress 'c' to continue.....   ");
        String enter=input.next();
        mainfunction();
    }

    public static void Exit(){
        System.out.println("\nApplication terminated successfully :) ");
        System.exit(0);
    }
    public static void HomePage() {
        System.out.println("\n\nWelcome " + CurrentUser.getNamee() + "\n\n1.Add Money\n2.Send Money\n3.Mobile Recharge" +
                "\n4.DTH Recharge\n5.Electricity\n6.Save contact\n7.Check Balance\n8.History\n9.Exit\n\n ");
        System.out.print("Enter your input : ");

        int Homeinput = input.nextInt();
        switch (Homeinput) {
            case 1:
                acc.addMoney();
                break;
            case 2:
                acc.sendMoney();
                break;
            case 3:
                acc.mobileRecharge();
                break;
            case 4:
                acc.DTHRecharge();
                break;
            case 5:
                acc.electricity();
                break;
            case 6:
                con.saveContact();
                break;
            case 7:
                acc.checkBalance();
                break;
            case 9:
                Exit();
            default:
                break;
        }
    }

}
class Contacts extends Account{
        String ContactNumber,Name;

        public String getName() {
            return Name;
        }
        public String getContactNumber() {
            return ContactNumber;
        }
        public void setName(String name) {
            Name = name;
        }
        public void setContactNumber(String contactNumber) {
            ContactNumber = contactNumber;
        }


        public void saveContact(){
            int count=0;
            if(ContactList.size()>=1){
                System.out.println("\nYour saved contacts :\n");
                Iterator ContactListIterator = ContactList.entrySet().iterator();
                while (ContactListIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry)ContactListIterator.next();
                    ++count;
                    System.out.println(count +" . "+mapElement.getKey() + " : " + mapElement.getValue());
                }
            }
            System.out.print("\nEnter the number to save in your contact list : ");
            input.nextLine();
            Name = input.nextLine();
            System.out.print("\nEnter the user name : ");
            ContactNumber = input.nextLine();
            setName(Name);
            setContactNumber(ContactNumber);
            ContactList.put(Name, ContactNumber);
            System.out.println("\nContact number added successfully :) \n");
            System.out.print("\nPress 'c' to continue.....   ");
            String enter=input.next();
            Main.HomePage();
        }

}



