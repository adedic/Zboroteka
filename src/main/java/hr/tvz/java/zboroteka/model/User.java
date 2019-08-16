package hr.tvz.java.zboroteka.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "`user`")
@SequenceGenerator(name = "seq", initialValue = 9, allocationSize = 100)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3988238308282615295L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "id", nullable = false, unique = true, updatable = true)
	private Integer id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "family_name", nullable = false)
	private String familyName;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	@Fetch(FetchMode.JOIN)
	private Set<Role> roles = new HashSet<>();

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "date_of_registration", nullable = false)
	private Date dateOfRegistration;

	@Column(name = "date_last_edited")
	private Date dateLastEdited;

	@Column(name = "sex")
	private Integer sex;

	@OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
	private List<Song> userSongs;

	public String getFullName() {
		return firstName + " " + familyName;
	}

}
