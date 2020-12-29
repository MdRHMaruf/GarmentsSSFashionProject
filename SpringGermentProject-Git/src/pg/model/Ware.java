package pg.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ware")
public class Ware implements Serializable{
	int id;
	String name;
	int acces;
	int user;
	String address;
	String email;

	String theme;
	String phone;
	String vat;
	int ware;
	int acc_ledger;
	int ware_type;
	int trace;
	
	

/*	public ware(int id, String name, String address, String email, String theme, String phone, String vat) {

		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.theme = theme;
		this.phone = phone;
		this.vat = vat;
	}
	
	public ware(String name, String address, String theme, String phone, String vat) {

		this.name = name;
		this.address = address;
		this.theme = theme;
		this.phone = phone;
		this.vat = vat;
	}*/
	
	public Ware() {
		
	}
	
	public Ware(String name, String address) {
		
		this.name = name;
		this.address = address;
	}
	


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAcces() {
		return acces;
	}
	public void setAcces(int acces) {
		this.acces = acces;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public int getWare() {
		return ware;
	}
	public void setWare(int ware) {
		this.ware = ware;
	}
	public int getAcc_ledger() {
		return acc_ledger;
	}
	public void setAcc_ledger(int acc_ledger) {
		this.acc_ledger = acc_ledger;
	}
	public int getWare_type() {
		return ware_type;
	}
	public void setWare_type(int ware_type) {
		this.ware_type = ware_type;
	}
	public int getTrace() {
		return trace;
	}
	public void setTrace(int trace) {
		this.trace = trace;
	}


}
