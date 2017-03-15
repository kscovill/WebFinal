package jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

@Entity
public class Login {

    @Id
    private String username;
    
//    
//    private String password;

//    private byte[] salt; 
    
//	public Long getId() {
//		return id;
//	}
//
//
//	public void setId(Long id) {
//		this.id = id;
//	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


//	public String getPassword() {
//		return password;
//	}
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	
//	public void setSalt(byte[] salt){
//		this.salt = salt;
//	}
//    
//	public byte[] getSalt(){
//		return salt;
//	}

}