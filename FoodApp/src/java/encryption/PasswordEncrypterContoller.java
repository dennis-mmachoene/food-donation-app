/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package encryption;

/**
 *
 * @author Dennis
 */
public interface PasswordEncrypterContoller {
   public String encrypt(String password) throws Exception;
   public String decrypt(String encryptedPassword) throws Exception;
   
}
