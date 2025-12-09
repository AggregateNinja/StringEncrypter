/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stringencrypter;

import DAOS.SysLogDAO;
import DAOS.SysOpDAO;
import DOS.SysLog;
import DOS.SysOps;
import Utility.Encryptor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Ryan
 */
public class StringEncrypter {

    static String value;
    static String key;
    static boolean encrypt;
    static String output;
    static String output2;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length < 3 || args[0].equalsIgnoreCase("h")){
            System.out.println("StringEncrypter - Encrypts and Decrypts String values\n\n"
                    + "  Usage: StringEncrypter 1 2 3\n"
                    + "    1 - String to be encrypted or decrypted\n"
                    + "    2 - Encryption Key to be used. Enter * for default\n"
                    + "    3 - To Encrypt 1, to Decrypt 0\n"
                    + "  Example: ...*.jar JohnDoe 1234abcd 1");
            
        }
        // jp9vO0yRq++QmUtQ/SFJeA== raven29desk 0
        
        value = args[0];
        if(args[1].equals("*")){
            key = "raven29desk";
        }else{
            key = args[1];
        }
        if (args[2].equals("0")) {
            encrypt = false;
        } else {
            encrypt = true;
        }
        
        if (value.equalsIgnoreCase("sys_ops")) {
            SysOpDAO soda = new SysOpDAO();
            HashMap<String, SysOps> hmSysOps = soda.getAllSysOps();
            HashMap<Integer, SysOps> hmEncryptedSysOps = soda.getAllEncryptedSysOps();

            int id = 0;
            String name = "", setting = "", option = "", encName = "", encSetting = "", encOption = "";
            for (Map.Entry row : hmSysOps.entrySet()) {
                SysOps sysOp = (SysOps) row.getValue();
                id = sysOp.getId();
                name = sysOp.getName();
                setting = sysOp.getSetting();
                option = sysOp.getOption();
                
                System.out.println("Id: " + id + ", Decrypted Name: " + name + ", Decrypted Setting: " + setting + ", Decrypted Option: " + option);
                
                SysOps encSysOp = hmEncryptedSysOps.get(id);
                encName = encSysOp.getName();
                encSetting = encSysOp.getSetting();
                encOption = encSysOp.getOption();
                
                System.out.println("Encrypted Name: " + encName + ", Encrypted Setting: " + encSetting + ", Encrypted Option: " + encOption + "\n");
            }
        } else if (value.equalsIgnoreCase("sysLog")) {
            SysLogDAO sldao = new SysLogDAO();
            
            TreeMap<Integer, SysLog> hmSysLog = sldao.getAllSysLog();
            HashMap<Integer, SysLog> hmEncryptedSysLog = sldao.getAllEncryptedSysLog();

            int id = 0, userId = 0;
            String action = "", message = "", created = "", encAction = "", encMessage = "";
            for (Map.Entry row : hmSysLog.entrySet()) {
                SysLog sysLog = (SysLog) row.getValue();
                id = sysLog.getId();
                userId = sysLog.getUserId();
                action = sysLog.getAction();
                message = sysLog.getMessage();
                created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysLog.getCreated());
                
                System.out.println("Id: " + id + ", UserId: " + userId + ", Created: " + created + "\nDecrypted Action: " + action + "\nDecrypted Message: " + message);
                
                SysLog encSysLog = hmEncryptedSysLog.get(id);
                encAction = encSysLog.getAction();
                encMessage = encSysLog.getMessage();
                
                System.out.println("Encrypted Action: " + encAction + "\nEncrypted Message: " + encMessage + "\n");
            }
            
        } else {
            if(key == null || key.isEmpty()){
                output = Encryptor.EncryptString(value, encrypt);
                output2 = Encryptor.EncryptString(output, !encrypt);
                
                
            }else{
                output = Encryptor.EncryptString(value, key, encrypt);
                output2 = Encryptor.EncryptString(output, key, !encrypt);
            }
            
            /*
            System.out.println("Input String: " + value);
            System.out.println("Key: " + key);
            if(encrypt == true){
                System.out.println("Action: Encrypting");
            }else{
                System.out.println("Action: Decrypting");
            }
            System.out.println("Output: " + output);
            */

            System.out.println("Input String: " + value);
            System.out.println("Key: " + key);
            System.out.println("Action: Encrypting");
            System.out.println("Output: " + output);
            System.out.println("Now Decryptiing...\n"
                    + "Decrypted Value: " + output2);
        }
        
        
       
        
        
        
        
        
        
    }
    
}
