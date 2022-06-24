import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;


public class Main {

    static Scanner input = new Scanner(System.in);
    static int TempUserID;
    static String sql;
    static DataBase db = new DataBase();

    public static void main(String[] args) throws SQLException {

        //Connecting To DataBase
        db.ConnectDB("jdbc:h2:PaymentDB");
        System.out.println("Connected to H2 embedded database.");

        //Creating DataBase Tables
        sql = "CREATE TABLE IF NOT EXISTS Users (UserID int primary key, name varchar(50) , mobile varchar(12),pin int,balance int)";
        db.statement.execute(sql);
        sql = "CREATE TABLE IF NOT EXISTS ContactList (ContactID int primary key,UserID int,name varchar(50),mobile varchar(12))";
        db.statement.execute(sql);
        sql = "CREATE TABLE IF NOT EXISTS TransactionHistory (TransactionID int primary key,UserID int,Type varchar(50),To varchar(50),Amount int,Date varchar(50))";
        db.statement.execute(sql);
        sql = "CREATE TABLE IF NOT EXISTS MoneyTransferredList (MoneyTransferredID int primary key,UserID int,mobile varchar(12))";
        db.statement.execute(sql);
        sql = "CREATE TABLE IF NOT EXISTS MobileRechargedList (MobileRechargedID int primary key,UserID int,mobile varchar(12))";
        db.statement.execute(sql);


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
        System.out.print("Mobile : ");
        String mobileinput = input.next();
        System.out.print("Pin : ");
        int pininput = input.nextInt();
        int index = 0;

        //Fetching all users from Db
        sql = "SELECT * FROM Users";
        try {
            ResultSet resultSet = db.statement.executeQuery(sql);
            while (resultSet.next()) {

                //Validating the Inputs with records
                int id = resultSet.getInt("UserID");
                int pin = resultSet.getInt("pin");
                String mobile = resultSet.getString("mobile");
                if (mobileinput.equals(mobile) && pininput == pin) {
                    TempUserID = id;
                    System.out.println("\nLogin successful  :)\n");
                    HomePage();
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("\nInvalid Try again\n");
        enterToContinue();
        mainfunction();
    }

    public static void Signup() {
        System.out.println("\n");
        input.nextLine();
        System.out.print("UserName : ");
        String usernameinput = input.nextLine();
        System.out.print("Mobile : ");
        String mobileinput = input.next();
        System.out.print("Pin : ");
        int pininput = input.nextInt();
        int balance = 0;
        int useridd = 0;
        boolean Userexist = false;

        //Checking if the user already exist
        sql = "SELECT * FROM Users";
        try {
            ResultSet result = db.statement.executeQuery(sql);
            while (result.next()) {
                String mobile = result.getString("mobile");
                if (mobileinput.equals(mobile)) {
                    Userexist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Userexist) {
            System.out.println("\n\nUser already exist please try to login\n\n");
        } else {
            //Counting the no.of users in record
            sql = "SELECT * FROM Users";
            try {
                ResultSet result = db.statement.executeQuery(sql);
                while (result.next()) {
                    useridd++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Creating a new record for user
            sql = "Insert into Users (UserID, name,mobile,pin,balance) values (" + (useridd + 1) + ", '" + usernameinput + "'," + mobileinput + "," + pininput + "," + balance + ")";
            try {
                db.statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("\nAccount Created successful :)\n");
        }
        enterToContinue();
        mainfunction();


    }

    public static void Exit() {
        System.out.println("\nApplication terminated successfully :) ");
        System.exit(0);
    }

    public static void HomePage() {
        System.out.println("\n\nWelcome " + "\n\n1.Add Money\n2.Send Money\n3.Mobile Recharge" +
                "\n4.DTH Recharge\n5.Electricity\n6.Save contact\n7.Check Balance\n8.History\n9.Logout\n\n ");
        System.out.print("Enter your input : ");

        int Homeinput = input.nextInt();
        switch (Homeinput) {
            case 1:
                addMoney();
                break;
            case 2:
                sendMoney();
                break;
            case 3:
                mobileRecharge();
                break;
            case 4:
                DTHRecharge();
                break;
            case 5:
                electricity();
                break;
            case 6:
                saveContact();
                break;
            case 7:
                checkBalance();
                break;
            case 8:
                history();
                break;
            case 9:
                mainfunction();
            default:
                break;
        }
    }

    public static void addMoney() {
        System.out.print("\nEnter the amount to deposit:");
        double amount1 = input.nextDouble();

        //Getting the Current Balance
        sql = "SELECT * FROM Users WHERE UserID=" + TempUserID + "";
        int tempbalance = 0;
        try {
            ResultSet resultSet = db.statement.executeQuery(sql);
            while (resultSet.next()) {
                tempbalance = resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Updating Balance
        sql = "UPDATE Users SET balance = " + (tempbalance + amount1) + " WHERE UserID =" + TempUserID + "";
        try {
            db.statement.execute(sql);
            System.out.println("\nThe amount '" + amount1 + "' added successfully to your account :) \n");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        enterToContinue();
        Main.HomePage();
    }

    public static void sendMoney() {
        MoneyTransferredContacts();
        SavedContacts();

        System.out.print("\nEnter the mobile number to send money : ");
        String sendmobilenum = input.next();
        System.out.print("\nEnter the amount to send :");
        int sendmoney = input.nextInt();


        //Checking the Available balance
        int currentBalance = getCurrentBalance();
        if (currentBalance > sendmoney && sendmobilenum.matches("[9876][0-9]{9}")) {
            updateBalance(sendmoney);
            SaveTransactionHistory("Money Transfer", sendmobilenum, sendmoney);
            int Tempid = 0;
            sql = "SELECT * FROM MoneyTransferredList";
            try {
                ResultSet result = db.statement.executeQuery(sql);
                while (result.next()) {
                    Tempid++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Updating new MoneyTransferredContact
            sql = "Insert into MoneyTransferredList (MoneyTransferredID,UserID,mobile) values (" + (Tempid + 1) + "," + TempUserID + "," + sendmobilenum + ")";
            try {
                db.statement.execute(sql);
                System.out.println("\nSuccessfully transfered the amount '" + sendmoney + "' to the mobile number '" + sendmobilenum + "'\n");
                enterToContinue();
                Main.HomePage();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Invalid try again\n\nSolution :  Check your Account balance or Enter a valid mobile number");
            enterToContinue();
            Main.HomePage();
        }

    }

    public static void mobileRecharge() {
        MobileRechargedContacts();
        SavedContacts();

        System.out.print("\nPlans available\n");
        System.out.print("\n1. 182 plan(voice-N/A,internet-available)\n2. 299 plan(voice,internet-available)\n3. 399 plan(voice,internet-available) \n");
        System.out.print("\nEnter the mobile number to recharge : ");
        String mobilenum = input.next();
        System.out.print("\nEnter the plan : ");
        int plan = input.nextInt();
        int currentBalance = getCurrentBalance();
        switch (plan) {
            case 1:
                if (currentBalance > 182 && mobilenum.matches("[9876][0-9]{9}")) {
                    updateBalance(182);
                    SaveTransactionHistory("Mobile Recharge", mobilenum, 182);
                    int Tempid = 0;
                    sql = "SELECT * FROM MobileRechargedList";
                    try {
                        ResultSet result = db.statement.executeQuery(sql);
                        while (result.next()) {
                            Tempid++;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                        //Updating new MoneyRechargedList
                    sql = "Insert into MobileRechargedList (MobileRechargedID,UserID,mobile) values (" + (Tempid + 1) + "," + TempUserID + "," + mobilenum + ")";
                    try {
                        db.statement.execute(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("\nRecharge of '182.0' is successful for the number '" + mobilenum + "'\nBenefits:Unlimited Data,no Voice/SMS\n");
                    enterToContinue();
                    break;
                } else {
                    System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance or Enter a valid mobile number\n");
                    enterToContinue();
                    Main.HomePage();
                }
                case 2:
                    if (currentBalance > 299 && mobilenum.matches("[9876][0-9]{9}")) {
                        updateBalance(299);
                        SaveTransactionHistory("Mobile Recharge", mobilenum, 299);
                        int Tempid = 0;
                        sql = "SELECT * FROM MobileRechargedList";
                        try {
                            ResultSet result = db.statement.executeQuery(sql);
                            while (result.next()) {
                                Tempid++;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        //Updating new MoneyRechargedList
                        sql = "Insert into MobileRechargedList (MobileRechargedID,UserID,mobile) values (" + (Tempid + 1) + "," + TempUserID + "," + mobilenum + ")";
                        try {
                            db.statement.execute(sql);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("\nRecharge of '299.0' is successful for the number '" + mobilenum + "\nBenefits:Unlimited Data,no Voice/SMS\n");
                        enterToContinue();
                        break;
                    } else {
                        System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance or Enter a valid mobile number\n");
                        enterToContinue();
                        Main.HomePage();
                    }
                case 3:
                    if (currentBalance > 399 && mobilenum.matches("[9876][0-9]{9}")) {
                        updateBalance(399);
                        SaveTransactionHistory("Mobile Recharge", mobilenum, 399);
                        int Tempid = 0;
                        sql = "SELECT * FROM MobileRechargedList";
                        try {
                            ResultSet result = db.statement.executeQuery(sql);
                            while (result.next()) {
                                Tempid++;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //Updating new MoneyRechargedList
                        sql = "Insert into MobileRechargedList (MobileRechargedID,UserID,mobile) values (" + (Tempid + 1) + "," + TempUserID + "," + mobilenum + ")";
                        try {
                            db.statement.execute(sql);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("\nRecharge of '399.0' is successful for the number '" + mobilenum + "'\nBenefits:Unlimited Data,no Voice/SMS\n");
                        enterToContinue();
                        break;
                    } else {
                        System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance or Enter a valid mobile number\n");
                        enterToContinue();
                        Main.HomePage();
                    }
        }
    }

    public static void DTHRecharge() {
        input.nextLine();
        System.out.print("\nEnter the Customer id : ");
        String customerId = input.next();
        System.out.print("\nEnter the amount to recharge : ");
        int DTHAmount = input.nextInt();
        int currentBalance = getCurrentBalance();
        if (currentBalance > DTHAmount) {
            updateBalance(DTHAmount);
            SaveTransactionHistory("DTH Recharge", customerId, DTHAmount);
            System.out.println("\nSuccessfully recharged for amount '" + DTHAmount + "' :)\n");
        } else {
            System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance\n");
        }
        enterToContinue();
        Main.HomePage();
    }

    public static void electricity() {
        input.nextLine();
        System.out.print("\nEnter the State : ");
        String State = input.nextLine();
        System.out.print("\nEnter the customerId : ");
        String customerId = input.next();
        System.out.print("\nEnter the amount to pay electricity bill:");
        int ElectricityAmount = input.nextInt();
        int currentBalance = getCurrentBalance();
        if (currentBalance > ElectricityAmount) {
            updateBalance(ElectricityAmount);
            SaveTransactionHistory("Electricity", customerId, ElectricityAmount);
            System.out.println("\nSuccessfully paid the amount '" + ElectricityAmount + "' :)\n");
        } else {
            System.out.println("\nInvalid try again\n\nSolution :  Check your Account balance\n");
        }
        enterToContinue();
        Main.HomePage();

    }

    public static void checkBalance() {

        int currentBalance = getCurrentBalance();
        System.out.println("\nBalance is : " + currentBalance);

        System.out.print("\nPress 'c' to continue.....   ");
        String enter = input.next();
        Main.HomePage();

    }

    public static void saveContact() {

        SavedContacts();

        System.out.print("\nEnter the number to save in your contact list : ");
        input.nextLine();
        String ContactNumber = input.nextLine();
        System.out.print("\nEnter the user name : ");
        String ContactName = input.nextLine();
        int Tempid = 0;
        sql = "SELECT * FROM ContactList";
        try {
            ResultSet result = db.statement.executeQuery(sql);
            while (result.next()) {
                Tempid++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Updating new Contact
        sql = "Insert into ContactList (ContactID,UserID, name,mobile) values (" + (Tempid + 1) + "," + TempUserID + ",'" + ContactName + "'," + ContactNumber + ")";
        try {
            db.statement.execute(sql);
            System.out.println("\nContact number added successfully :) \n");
            System.out.print("\nPress 'c' to continue.....   ");
            String enter = input.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Main.HomePage();
    }

    public static void history() {
        int count = 0;
        sql = "SELECT * FROM TransactionHistory WHERE UserID=" + TempUserID + "";
        try {
            ResultSet resultSet = db.statement.executeQuery(sql);
            while (resultSet.next()) {
                count++;

            }
            if (count > 0) {
                System.out.println("\n\nYour TransactionHistory");
                ResultSet resultSet1 = db.statement.executeQuery(sql);
                while (resultSet1.next()) {
                    int TransactionID = resultSet1.getInt("TransactionID");
                    String Type = resultSet1.getString("Type");
                    String To = resultSet1.getString("To");
                    int Amount = resultSet1.getInt("Amount");
                    String Date = resultSet1.getString("Date");
                    System.out.print("\nID:" + TransactionID + " | Type: " + Type + " | To: " + To + " | Amount: " + Amount + " | Date: " + Date);

                }
                enterToContinue();
            } else {

                System.out.print("\n\nNo TransactionHistory found\n\n\n ");
                enterToContinue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Main.HomePage();

    }

    public static int getCurrentBalance() {

        //Getting the Current Balance
        sql = "SELECT * FROM Users WHERE UserID=" + TempUserID + "";
        int tempbalance = 0;
        try {
            ResultSet resultSet = db.statement.executeQuery(sql);
            while (resultSet.next()) {
                tempbalance = resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempbalance;
    }

    public static void updateBalance(int Amount) {
        int currentBalance = getCurrentBalance();
        //Updating Balance
        sql = "UPDATE Users SET balance = " + (currentBalance - Amount) + " WHERE UserID =" + TempUserID + "";
        try {
            db.statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void SavedContacts() {
        //Fetching Saved Contacts
        int count = 0;
        sql = "SELECT * FROM ContactList WHERE UserID=" + TempUserID + "";
        try {
            ResultSet resultSet = db.statement.executeQuery(sql);
            while (resultSet.next()) {
                count++;
            }
            if (count > 0) {
                System.out.println("\nYour Saved Contacts :");
                ResultSet resultSet1 = db.statement.executeQuery(sql);
                int count1 = 0;
                while (resultSet1.next()) {
                    count1++;
                    String ContactName = resultSet1.getString("name");
                    String ContactNumber = resultSet1.getString("mobile");
                    System.out.println(count1 + ". " + ContactName + " - " + ContactNumber);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void MoneyTransferredContacts() {
        int count = 0;
        sql = "SELECT * FROM MoneyTransferredList WHERE UserID=" + TempUserID + "";
        try {
            ResultSet resultSet = db.statement.executeQuery(sql);
            while (resultSet.next()) {
                count++;
            }
            if (count > 0) {
                System.out.println("\nYour Previous Money Transferred Contacts :");
                ResultSet resultSet1 = db.statement.executeQuery(sql);
                int count1 = 0;
                while (resultSet1.next()) {
                    count1++;
                    String ContactNumber = resultSet1.getString("mobile");
                    System.out.println(count1 + ". " + ContactNumber);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void MobileRechargedContacts() {
        int count = 0;
        sql = "SELECT * FROM MobileRechargedList WHERE UserID=" + TempUserID + "";
        try {
            ResultSet resultSet = db.statement.executeQuery(sql);
            while (resultSet.next()) {
                count++;
            }
            if (count > 0) {
                System.out.println("\nYour Previous Mobile Recharged Contacts :");
                ResultSet resultSet1 = db.statement.executeQuery(sql);
                int count1 = 0;
                while (resultSet1.next()) {
                    count1++;
                    String ContactNumber = resultSet1.getString("mobile");
                    System.out.println(count1 + ". " + ContactNumber);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void SaveTransactionHistory(String Type, String To, int Amount) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String Date = formatter.format(date);

        int Tempid = 0;
        sql = "SELECT * FROM TransactionHistory";
        try {
            ResultSet resultSet = db.statement.executeQuery(sql);
            while (resultSet.next()) {
                Tempid++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "Insert into TransactionHistory (TransactionID, UserID,Type,To,Amount,Date) values (" + (Tempid + 1) + "," + TempUserID + ",'" + Type + "','" + To + "'," + Amount + ",'" + Date + "')";
        try {
            db.statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void enterToContinue() {
        System.out.print("\n\nPress 'c' to continue.....   \n");
        String enter = input.next();
    }
}

class DataBase {
    public static  Connection connection;
    public static Statement statement;

    public void ConnectDB(String url)  {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }
}

