package logic;
import org.json.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PasswordManager {
    public static List<Account> accountList = new ArrayList<>();
    private static final String path = "src/main/java/logic/database.json";
    public static void main(String[] args) {
        // Always
        updateAccounts(path);
        /*addAccount("cubik.com", "Qian", "12345");
        getListData();
        addAccount("www.cihaozhang.com", "Cihao Zhang", "12345");
        getListData();
        modifyAccount("www.cihaozhang.com", "Cihao Zhang", "12345","1231");
        getListData();*/
        getListData();
    }

    public static void deleteAccount(String website, String username) {
        try {
            JSONObject obj = new JSONObject(getContent());
            JSONArray arr = obj.getJSONArray("data");
            if (arr.isEmpty()) {
                System.out.println("It is empty! Please try again!");
                return;
            }
            for (int i = 0; i < arr.length(); i++) {
                if (arr.getJSONObject(i).getString("website").equals(website) &&
                        arr.getJSONObject(i).getString("username").equals(username)) {
                    arr.remove(i);
                    System.out.println("Succesfully removed " + username + " from database");
                    updateAccounts(path);


                    FileOutputStream fos = new FileOutputStream(path);
                    fos.write(obj.toString().getBytes());
                    fos.close();
                    return;
                }
            }


            System.out.println("User or website doesn't exist!");
        }
        catch (Exception ignored) {
            System.out.println("Sorry, something went wrong! Please try again.");
        }
    }

    public static void modifyAccount(String website, String username, String oldPassword, String newPassword) {
        try {
            JSONObject obj = new JSONObject(getContent());
            JSONArray arr = obj.getJSONArray("data");
            if (arr.isEmpty()) {
                System.out.println("It is empty! Please try again!");
                return;
            }
            if (oldPassword.equals(newPassword)) {
                System.out.println("It is the same password! Please try again!");
                return;
            }
            for (int i = 0; i < arr.length(); i++) {
                if (arr.getJSONObject(i).getString("website").equals(website)
                && arr.getJSONObject(i).getString("username").equals(username)
                && arr.getJSONObject(i).getString("password").equals(oldPassword)) {

                    arr.getJSONObject(i).put("password", newPassword);
                    System.out.println("Setting new password to " + newPassword);
                    FileOutputStream fos = new FileOutputStream(path);
                    fos.write(obj.toString().getBytes());
                    fos.close();
                    updateAccounts(path);
                    return;
                }
            }
            System.out.println("No website or username matches your search! Please try again!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            updateAccounts(path);
        }
    }

    private static String getContent() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    public static void updateAccounts(String path) {
        String loc = path;
        try {
            accountList.clear();
            String content = getContent();
            if (content.isEmpty()) {
                System.out.println("It is empty! Please try again!");
                JSONObject object = new JSONObject();
                object.put("data", new JSONArray());

                FileOutputStream fos = new FileOutputStream(loc);
                fos.write(object.toString().getBytes());
                fos.close();
                return;
            }
            JSONObject json = new JSONObject(content);
            if (!json.has("data")) {
                System.out.println("Data is empty!");
                JSONArray array = new JSONArray();
                json.put("data", array);

                FileOutputStream fos = new FileOutputStream(loc);
                fos.write(json.toString().getBytes());
                fos.close();

                return;
            }
            JSONArray array = json.getJSONArray("data");
            if (array.isEmpty()) {
                System.out.println("Data is empty!");
                return;
            }
            for (int i = 0; i < array.length(); i++) {
                /*System.out.println(array.getJSONObject(i).getString("username"));*/
                JSONObject temp = array.getJSONObject(i);
                accountList.add(new Account(temp.getString("username"),
                        temp.getString("password"), temp.getString("website")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addAccount(String website, String username, String password) {
       try {
           JSONObject obj = new JSONObject(getContent());
           JSONArray arr = obj.getJSONArray("data");
           JSONObject objCreate = new JSONObject();
           objCreate.put("username", username);
           objCreate.put("password", password);
           objCreate.put("website", website);

           // Checks if the user already registered in website
           for (int i = 0; i < arr.length(); i++) {
               if (arr.getJSONObject(i).getString("website").equals(website) &&
               arr.getJSONObject(i).getString("username").equals(username)) {
                   System.out.println("You already created this account!");
                   return;
               }
           }
           arr.put(objCreate);
           for (int i = 0; i < arr.length(); i++) {
               System.out.println(arr.getJSONObject(i).getString("username"));
               System.out.println(arr.getJSONObject(i).getString("password"));
               System.out.println(arr.getJSONObject(i).getString("website"));
               System.out.println("\n--------");
           }
           FileOutputStream outputStream = new FileOutputStream(path);
           System.out.println(obj.toString());
            outputStream.write(obj.toString().getBytes());
           updateAccounts(path);
           outputStream.close();
       }
       catch (IOException e) {
           e.printStackTrace();
       }
       catch (Exception e) {
           System.out.println("Something went wrong!");
           e.printStackTrace();
       }
    }

    public static void getListData() {
        if (accountList.isEmpty()) {
            System.out.println("Accounts are empty for now!");
            return;
        }
        for (Account account : accountList) {
            System.out.println(account.getUsername());
            System.out.println(account.getPassword());
            System.out.println(account.getWebsiteName());
        }
    }
}
