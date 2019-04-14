package hr.tvz.java.zboroteka.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5681590906981415820L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 30)
	private Integer id;

	@Column(name = "role_name", nullable = false, length = 40)
	private String roleName;

	public Role() {
		// default constructor
	}

	public Role(String roleName) {
		this.roleName = roleName;
	}

	// --- get / set methods --------------------------------------------------

	@Override
	public String getAuthority() {
		return getRoleName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
