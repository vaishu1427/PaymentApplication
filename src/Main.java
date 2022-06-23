import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

//USER

class User {
    private  String  Namee , Address ,Mobile;
    private int  Pin ;
    private double  UserIdd ;
    protected double balance;
    protected ArrayList<String> MoneyTransferedContacts=new ArrayList<>();
    protected ArrayList<Contact> ContactList = new ArrayList<>();
    protected ArrayList<String> RechargedContacts=new ArrayList<>();
    protected ArrayList<TransactionHistory> TransactionHistorys = new ArrayList<>();

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
    public ArrayList<String> getMoneyTransferedContacts(){
        return MoneyTransferedContacts;
    }
    public ArrayList<String> getRechargedContacts(){
        return RechargedContacts;
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
    public void setMoneyTransferedContacts(String number){
        MoneyTransferedContacts.add(number);
    }
    public void setRechargedContacts(String number){
        RechargedContacts.add(number);
    }
    public double getBalance() {
        return balance;
    }
    
}

class Account extends User {


    static Scanner input=Main.input;

    public void addMoney() {
        System.out.print("\nEnter the amount to deposit:");
        double amount1=input.nextDouble();
        Main.Users.get(Main.CurrentUserIndex).balance = Main.Users.get(Main.CurrentUserIndex).balance + amount1;

        System.out.println("\nThe amount '"+amount1+"' added successfully to your account :) \n");
        System.out.print("\nPress 'c' to continue.....   ");
        String enter=input.next();
        Main.HomePage();
    }
    
    public void sendMoney() {
        int count1=0;
        if(Main.Users.get(Main.CurrentUserIndex).getMoneyTransferedContacts().size()>=0){
            if(Main.Users.get(Main.CurrentUserIndex).getMoneyTransferedContacts().size()>0) {
                System.out.println("\nContacts of previous money transaction :");
            }
            for (String i : Main.Users.get(Main.CurrentUserIndex).getMoneyTransferedContacts()) {
                System.out.println(count1 + 1 + " .  " + Main.Users.get(Main.CurrentUserIndex).getMoneyTransferedContacts().get(count1));
                count1++;
            }
            if(Main.Users.get(Main.CurrentUserIndex).ContactList.size()>=1){
                System.out.println("\nYour saved contacts :\n");
                int contactcount = 0;
                for (Contact ele: Main.Users.get(Main.CurrentUserIndex).ContactList
                ) {
                    System.out.println((contactcount+1)+". "+ele.Name+" - "+ele.ContactNumber);
                    contactcount++;
                }

            }
        }
        System.out.print("\nEnter the mobile number to send money : ");
        String sendmobilenum=input.next();
        System.out.print("\nEnter the amount to send :");
        int sendmoney=input.nextInt();
        if(Main.Users.get(Main.CurrentUserIndex).balance>sendmoney &&  sendmobilenum.matches("[9876][0-9]{9}")) {
            Main.Users.get(Main.CurrentUserIndex).setMoneyTransferedContacts(sendmobilenum);
            SaveTransaction("Money Transfer",sendmobilenum,sendmoney);
            System.out.println("\nSuccessfully transfered the amount '"+sendmoney+"' to the mobile number '"+sendmobilenum+"'\n");
            Main.Users.get(Main.CurrentUserIndex).balance =Main.Users.get(Main.CurrentUserIndex).balance-sendmoney;
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
        if(Main.Users.get(Main.CurrentUserIndex).getRechargedContacts().size()>=0){
            for (String i : Main.Users.get(Main.CurrentUserIndex).getRechargedContacts()) {
                if(count2==0){
                    System.out.println("\nContacts of previous mobile recharge :\n");
                }
                System.out.println(count2 + 1 + " .  " + Main.Users.get(Main.CurrentUserIndex).getRechargedContacts().get(count2));
                count2++;
            }
            if(Main.Users.get(Main.CurrentUserIndex).ContactList.size()>=1){
                System.out.println("\nYour saved contacts :\n");
                int contactcount = 0;
                for (Contact ele: Main.Users.get(Main.CurrentUserIndex).ContactList
                ) {
                    System.out.println((contactcount+1)+". "+ele.Name+" - "+ele.ContactNumber);
                    contactcount++;
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

                if(Main.Users.get(Main.CurrentUserIndex).balance>182 &&  mobilenum.matches("[9876][0-9]{9}")) {
                    Main.Users.get(Main.CurrentUserIndex).balance=Main.Users.get(Main.CurrentUserIndex).balance-182;
                    Main.Users.get(Main.CurrentUserIndex).setRechargedContacts(mobilenum);
                    SaveTransaction("Mobile Recharge",mobilenum,182);
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

                if(Main.Users.get(Main.CurrentUserIndex).balance>299 &&  mobilenum.matches("[9876][0-9]{9}")) {
                    Main.Users.get(Main.CurrentUserIndex).balance=Main.Users.get(Main.CurrentUserIndex).balance-299;
                    Main.Users.get(Main.CurrentUserIndex).setRechargedContacts(mobilenum);
                    SaveTransaction("Mobile Recharge",mobilenum,299);
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

                if(Main.Users.get(Main.CurrentUserIndex).balance>399 &&  mobilenum.matches("[9876][0-9]{9}")) {
                    Main.Users.get(Main.CurrentUserIndex).balance=Main.Users.get(Main.CurrentUserIndex).balance-399;
                    Main.Users.get(Main.CurrentUserIndex).setRechargedContacts(mobilenum);
                    SaveTransaction("Mobile Recharge",mobilenum,399);
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
        if(Main.Users.get(Main.CurrentUserIndex).balance>DTHAmount) {
            Main.Users.get(Main.CurrentUserIndex).balance = Main.Users.get(Main.CurrentUserIndex).balance - DTHAmount;
            SaveTransaction("DTH Recharge",customerId,DTHAmount);
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
        if(Main.Users.get(Main.CurrentUserIndex).balance>ElectricityAmount) {
            Main.Users.get(Main.CurrentUserIndex).balance = Main.Users.get(Main.CurrentUserIndex).balance - ElectricityAmount;
            SaveTransaction("Electricity",customerId,ElectricityAmount);
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
        System.out.println("\nBalance is : "+Main.Users.get(Main.CurrentUserIndex).balance);
        System.out.print("\nPress 'c' to continue.....   ");
        String enter=input.next();
        Main.HomePage();
    }

    public void saveContact(){
        int count=0;
        if(Main.Users.get(Main.CurrentUserIndex).ContactList.size()>=1){
            System.out.println("\nYour saved contacts :\n");
            int contactcount = 0;
            for (Contact ele: Main.Users.get(Main.CurrentUserIndex).ContactList
                 ) {
                System.out.println((contactcount+1)+". "+ele.Name+" - "+ele.ContactNumber);
                contactcount++;
            }

        }
        System.out.print("\nEnter the number to save in your contact list : ");
        input.nextLine();
        String ContactNumber = input.nextLine();
        System.out.print("\nEnter the user name : ");
        String Name = input.nextLine();
        Contact newcontact = new Contact();
        newcontact.setName(Name);
        newcontact.setContactNumber(ContactNumber);
        Main.Users.get(Main.CurrentUserIndex).ContactList.add(newcontact);
        System.out.println("\nContact number added successfully :) \n");
        System.out.print("\nPress 'c' to continue.....   ");
        String enter=input.next();
        Main.HomePage();
    }

    public void  history(){

        if (Main.Users.get(Main.CurrentUserIndex).TransactionHistorys.size()>0){
            int tcount =0;
            System.out.println("\n\nYour TransactionHistory");
            for (TransactionHistory ele: Main.Users.get(Main.CurrentUserIndex).TransactionHistorys
                 ) {
                System.out.print("\n"+(tcount+1)+". Type: "+ele.Type+"| To: "+ele.to+"| Amount: "+ele.Amount+"| Date: "+ele.date);
            }
            System.out.print("\n\n\nPress 'c' to continue.....   ");
            String enter=input.next();
            Main.HomePage();
        }else {

            System.out.print("\n\nNo TransactionHistory found\n\n\nPress 'c' to continue.....   ");
            String enter=input.next();
            Main.HomePage();
        }

    }

    public void SaveTransaction(String Type ,String to, int Amount ){
        TransactionHistory newTransaction = new TransactionHistory();
        newTransaction.setType(Type);
        newTransaction.setAmount(Amount);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        newTransaction.setDate(formatter.format(date));
        newTransaction.setTo(to);
        Main.Users.get(Main.CurrentUserIndex).TransactionHistorys.add(newTransaction);
    }


}

//MAIN


public class Main {
    static Scanner input = new Scanner(System.in);
    static Account acc = new Account();
    static List<User> Users = new ArrayList<>();
    static boolean UserAuth = false;
    static int CurrentUserIndex;

    public static void main(String[] args) {
        String usernameinput = "Vaishu";
        String addressinput = "Coimbatore";
        String mobileinput = "9874563210";
        int pininput = 12345;
        int useridd = Users.size() + 1;
        User NewUser = new User();
        NewUser.setNamee(usernameinput);
        NewUser.setAddress(addressinput);
        NewUser.setMobile(mobileinput);
        NewUser.setPin(pininput);
        NewUser.setUserId(useridd);
        Users.add(NewUser);
        String usernameinput1 = "Udhaya";
        String addressinput1 = "Tiruppur";
        String mobileinput1 = "9874563211";
        int pininput1 = 1234;
        int useridd1 = Users.size() + 1;
        User NewUser1 = new User();
        NewUser1.setNamee(usernameinput1);
        NewUser1.setAddress(addressinput1);
        NewUser1.setMobile(mobileinput1);
        NewUser1.setPin(pininput1);
        NewUser1.setUserId(useridd1);
        Users.add(NewUser1);
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
        int index = 0;
        for (User ele : Users
        ) {
            if (ele.getUserId() == useridinput && ele.getPin()== pininput) {
                UserAuth = true;
                CurrentUserIndex = index;
                System.out.println("\nLogin successful  :)\n");
                HomePage();
                break;
            }
            index++;
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
        System.out.println("\n\nWelcome " + Users.get(CurrentUserIndex).getNamee() + "\n\n1.Add Money\n2.Send Money\n3.Mobile Recharge" +
                "\n4.DTH Recharge\n5.Electricity\n6.Save contact\n7.Check Balance\n8.History\n9.Logout\n\n ");
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
                acc.saveContact();
                break;
            case 7:
                acc.checkBalance();
                break;
            case 8:
                acc.history();
                break;
            case 9:
                mainfunction();
            default:
                break;
        }
    }

}

class TransactionHistory extends  Account{
    String Type , to ,date;
    double Amount;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }
}


class Contact extends Account{
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


}




