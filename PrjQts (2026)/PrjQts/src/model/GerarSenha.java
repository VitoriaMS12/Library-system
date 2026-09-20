package model;

import org.mindrot.jbcrypt.BCrypt;

public class GerarSenha {
    
    public static void main(String[] args) {
        
        String senha = "Teste12345";
        
        String hash = BCrypt.hashpw(
        senha,
        BCrypt.gensalt(10)
        );
        
        System.out.println(hash);
    }
    
}
