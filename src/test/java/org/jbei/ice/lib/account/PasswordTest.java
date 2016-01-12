/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbei.ice.lib.account;

import org.jbei.ice.lib.utils.UtilityException;
import org.jbei.ice.lib.utils.Utils;

/**
 *
 * @author prash
 */
public class PasswordTest {
    public static void main(String[] args) throws UtilityException {
        String randomSalt = "";
        //randomSalt = PasswordUtil.generateSalt();
        randomSalt = Utils.generateSaltForUserAccount();
        String encryptedPass = "";
        encryptedPass = AccountUtils.encryptNewUserPassword("guest", randomSalt);
        System.out.println("Salt :" + randomSalt);
        System.out.println("Password :"+encryptedPass);
        
    }
}
