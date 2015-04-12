/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Backend;

import static Backend.Admin.admins;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * SpecialistList class creates and array of specialists
 * it can add specialist to it, delete them
 * SpecialistList -> SpecialistList
 * @author Eric Sullivan
 */
public class SpecialistList{
    //declaration of the array specs wich will hold the specialists
    public static ArrayList<Specialist> specs = new ArrayList<Specialist>();
    
    public SpecialistList(){
    }

    //adds an specialist to the array
    public void addSpecialist(String photo, String email, String fName, String lname, String role, String phone)
    {
     specs.add(new Specialist(photo,email,fName,lname, role, phone));
    }
    public void addSpecialist(Specialist s){
        specs.add(s);
    }

    //deletes a specialist, iterates through the array and compare the username with the first name of the specialist
    //if it is equal, the specialist gets remove from the array
    public void deleteSpecialist(String username)
    {
     for (Specialist s : specs)
     {
      if(s.getfName().equals(username))
      {
       specs.remove(s);
      } 
     }
    }

    //gets the array of the specialists
    public static ArrayList<Specialist> getSpecialList() {
  return specs;
 }
    
    
    public static void serialize() {
     try{
            FileOutputStream fos= new FileOutputStream("specs");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            //System.out.println("lol");
            oos.writeObject(specs);
            oos.close();
            fos.close();
          }catch(IOException ioe){
               ioe.printStackTrace();
           }
    }
    
    public static void deSerialize() {
     try
        {
            FileInputStream fis = new FileInputStream("specs");
            ObjectInputStream ois = new ObjectInputStream(fis);
            specs = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
         }catch(IOException ioe){
             ioe.printStackTrace();
             return;
          }catch(ClassNotFoundException c){
             System.out.println("Class not found");
             c.printStackTrace();
             return;
          }
    }
}