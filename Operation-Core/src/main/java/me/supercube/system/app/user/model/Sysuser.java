package me.supercube.system.app.user.model;
// Generated 2016-9-13 11:32:38 by Hibernate Tools 4.3.1.Final

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Transient;
/**
 * Sysuser generated by hbm2java
 */
@Entity
@Table(name = "sysuser", uniqueConstraints = { @UniqueConstraint(columnNames = "userid"),
		@UniqueConstraint(columnNames = "loginid") })
public class Sysuser implements java.io.Serializable {

	private long sysuserid;//唯一标识
	private String userid;//用户,对所有用户记录必须唯一。
	private String truename;//真实姓名
	//1：活动 0：非活动用户
	private String status;//状态,只读字段，指示用户的状态是活动的还是不活动的。如为非活动状态，用户将无法登录到系统。您可以从“选择操作”菜单中，使用“变更状态”操作来变更用户状态。
	// SYSTEM=系统管理员类型  SPECIALIST=专家   FARMER=农户
	private String type;//类型,该系统含有用户类型 1 - 10。在 USERTYPE 域中，应该更新描述以反映许可证中的用户类型。合适的类型应与每个用户的标识关联，以确保许可证一致。  SYSTEM=系统管理员类型  SPECIALIST=专家   FARMER=农户
	private String pwhintquestion;//密码提示问题,为验证用户身份，用户或管理员选择询问的问题。在“域”应用程序中，管理员通过在 PWHINTQUESTION 域中添加值来定义密码提示问题。
	private String pwhintanswer;//答案,密码提示问题的正确答案。
	private boolean forceexpiration;//密码在第一次登录后失效,显示是否要求用户在下一次登录时变更密码。
	private LocalDateTime pwexpiration;//密码过期日期,如果使用“安全性控制”操作指定密码有效期，那么只读字段将显示密码截止日期。
	private int failedlogins;//登录失败,连续登录失败尝试次数
	private String loginid;//用户名,登录系统的用户名称。登录用户名可与用户标识相同，也可不同。
	private String password;//密码,登录系统（加密）的密码。
	private String salt;//密码盐值,密码盐值
	private String passwordold;//旧密码,密码原始值（当变更密码时）
	private String saltold;//旧密码盐值,当变更密码时,旧密码盐值
	private String databaseuserid;//数据库用户名,连接到系统应用程序（如果有）外部数据库的用户名。
	private String memo;//备忘录,如有需要，输入有关用户的附加信息。
	private boolean sysuser;//系统帐号,指定是否删除用户标识。如果选取该复选框，用户标识将为系统帐户并且无法删除。如果清除了复选框，用户标识即不是系统帐户，可以删除。安装时系统有 2 个系统用户（SYSADMIN 和 SYSREG），而且可添加其他用户。系统要求系统用户具有诸如时间任务和自行注册等功能。
	private LocalDateTime createdate;//创建日期
	private String department;//部门,该人员所属部门。
	private String title;//头衔,该人员的头衔。
	private String employeetype;//职员类别	,该人员所属的职员类别，如全职或兼职职员。
	private String jobcode;//	职务代码	jobcode	该人员的职务代码。
	private String supervisor;//主管人,该人员的主管姓名。
	private LocalDate birthdate;//出生日期	birthdate	该人员的出生日期。
	private LocalDateTime lastevaldate;//上一次评估,该人员的最新评估日期。
	private LocalDateTime nextevaldate;//下一次评估,该人员下次评估的日期。
	private LocalDateTime hiredate;//聘用日期,员工开始为公司工作的日期。
	private LocalDateTime terminationdate;//终止日期,对该人员聘用的终止日期。
	private String city;//城市,该人员居住的城市。
	private String regiondistrict;//地区/区域,地区/区域
	private String county;//国家或地区,国家或地区
	private String stateprovince;//省/直辖市/自治区,该人员居住的州或省可以为两个字符代码或完整拼写。
	private String country;//国家或地区,该人员所在国家或地区。
	private String postalcode;//邮政编码,该人员的邮政编码。
	private String email;//电子邮件	email	用于联系此人的主要电子邮件地址。
	private String phone;//电话号码,此联系人的主要电话号码
	private String mobile;//移动电话,移动电话
	private String ownergroup;//人员组	ownergroup	人员的缺省组。缺省组适用于属于一个组或多个组的人员记录，或者不属于任何组的人员记录。
	private String siteid;//项目站点ID	siteid	项目站点ID
	private String  passwordcheck;//确认密码,再次输入密码。系统将该条目与“密码”字段进行比较，以确保准确性。
	private String np_statusmemo;//变更状态备忘录,状态变更备忘录使用的临时非持久性字段。
	private boolean synchpasswords;//将数据库密码也变更为该密码,指定系统和数据库访问是否使用相同的密码。如果此复选框已选中，密码值将从“新密码”区域复制到“数据库密码”区域。如果此复选框未选中，密码可能相同，也可能不同。该字段只有在用户拥有数据库访问权时才可以进行编辑。
	private String dbpassword;//数据库密码	,本地数据库密码（当数据库用户标识非空时）
	private String dbpasswordcheck;//数据库密码校验,变更数据库密码时再次输入以确保没有类型
	private String passwordinput;//密码,用于存储输入的密码，然后对它进行加密并存储在数据库中的非持久性字段。
	private String generatedpswd;//查看密码,用于存储自动生成的密码，然后对它进行加密并存储在数据库中的非持久性字段。
	private String emailpswd;//以电子邮件将密码寄送给用户,指示是否应该将新的或变更后的密码以电子邮件寄送给用户
	private String statusiface;//状态已变更,非持久性布尔字段，指示从数据库访存全状态对象后状态是否已变更。
	private String imPassword;//即时消息传递密码,即时消息传递密码
	private String imgUrl; //保持用户头像路径

	private String speciType;//专家类型

	public Sysuser() {
	}

	public Sysuser(String userid, String truename, String status, String type, boolean forceexpiration,
			int failedlogins, String loginid, String password, String salt, boolean sysuser, String siteid) {
		this.userid = userid;
		this.truename = truename;
		this.status = status;
		this.type = type;
		this.forceexpiration = forceexpiration;
		this.failedlogins = failedlogins;
		this.loginid = loginid;
		this.password = password;
		this.salt = salt;
		this.sysuser = sysuser;
		this.siteid = siteid;
	}

	public Sysuser(String userid, String truename, String status, String type, String pwhintquestion,
			String pwhintanswer, boolean forceexpiration, LocalDateTime pwexpiration, int failedlogins, String loginid,
			String password, String salt, String passwordold, String saltold, String databaseuserid, String memo,
			boolean sysuser, LocalDateTime createdate, String department, String title, String employeetype, String jobcode,
			String supervisor, LocalDate birthdate, LocalDateTime lastevaldate, LocalDateTime nextevaldate, LocalDateTime hiredate,
			LocalDateTime terminationdate, String city, String regiondistrict, String county, String stateprovince,
			String country, String postalcode, String email, String phone, String mobile, String ownergroup,
			String siteid) {
		this.userid = userid;
		this.truename = truename;
		this.status = status;
		this.type = type;
		this.pwhintquestion = pwhintquestion;
		this.pwhintanswer = pwhintanswer;
		this.forceexpiration = forceexpiration;
		this.pwexpiration = pwexpiration;
		this.failedlogins = failedlogins;
		this.loginid = loginid;
		this.password = password;
		this.salt = salt;
		this.passwordold = passwordold;
		this.saltold = saltold;
		this.databaseuserid = databaseuserid;
		this.memo = memo;
		this.sysuser = sysuser;
		this.createdate = createdate;
		this.department = department;
		this.title = title;
		this.employeetype = employeetype;
		this.jobcode = jobcode;
		this.supervisor = supervisor;
		this.birthdate = birthdate;
		this.lastevaldate = lastevaldate;
		this.nextevaldate = nextevaldate;
		this.hiredate = hiredate;
		this.terminationdate = terminationdate;
		this.city = city;
		this.regiondistrict = regiondistrict;
		this.county = county;
		this.stateprovince = stateprovince;
		this.country = country;
		this.postalcode = postalcode;
		this.email = email;
		this.phone = phone;
		this.mobile = mobile;
		this.ownergroup = ownergroup;
		this.siteid = siteid;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sysuserid", unique = true, nullable = false)
	public Long getSysuserid() {
		return this.sysuserid;
	}

	public void setSysuserid(Long sysuserid) {
		this.sysuserid = sysuserid;
	}

	@Column(name = "userid", unique = true, nullable = false, length = 30)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "truename", nullable = false, length = 30)
	public String getTruename() {
		return this.truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	@Column(name = "status", nullable = false, length = 12)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "type", nullable = false, length = 30)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "pwhintquestion", length = 25)
	public String getPwhintquestion() {
		return this.pwhintquestion;
	}

	public void setPwhintquestion(String pwhintquestion) {
		this.pwhintquestion = pwhintquestion;
	}

	@Column(name = "pwhintanswer", length = 2000)
	public String getPwhintanswer() {
		return this.pwhintanswer;
	}

	public void setPwhintanswer(String pwhintanswer) {
		this.pwhintanswer = pwhintanswer;
	}

	@Column(name = "forceexpiration")
	public boolean isForceexpiration() {
		return this.forceexpiration;
	}

	public void setForceexpiration(boolean forceexpiration) {
		this.forceexpiration = forceexpiration;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "pwexpiration", length = 19)
	public LocalDateTime getPwexpiration() {
		return this.pwexpiration;
	}

	public void setPwexpiration(LocalDateTime pwexpiration) {
		this.pwexpiration = pwexpiration;
	}

	@Column(name = "failedlogins")
	public int getFailedlogins() {
		return this.failedlogins;
	}

	public void setFailedlogins(int failedlogins) {
		this.failedlogins = failedlogins;
	}

	@Column(name = "loginid", unique = true, nullable = false, length = 50)
	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	@Column(name = "password", nullable = false, length = 128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "salt", nullable = false, length = 128)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "passwordold", length = 35)
	public String getPasswordold() {
		return this.passwordold;
	}

	public void setPasswordold(String passwordold) {
		this.passwordold = passwordold;
	}

	@Column(name = "saltold", length = 35)
	public String getSaltold() {
		return this.saltold;
	}

	public void setSaltold(String saltold) {
		this.saltold = saltold;
	}

	@Column(name = "databaseuserid", length = 18)
	public String getDatabaseuserid() {
		return this.databaseuserid;
	}

	public void setDatabaseuserid(String databaseuserid) {
		this.databaseuserid = databaseuserid;
	}

	@Column(name = "memo", length = 256)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "sysuser", nullable = false)
	public boolean isSysuser() {
		return this.sysuser;
	}

	public void setSysuser(boolean sysuser) {
		this.sysuser = sysuser;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "createdate", length = 19)
	public LocalDateTime getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(LocalDateTime createdate) {
		this.createdate = createdate;
	}

	@Column(name = "department", length = 30)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "title", length = 30)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "employeetype", length = 10)
	public String getEmployeetype() {
		return this.employeetype;
	}

	public void setEmployeetype(String employeetype) {
		this.employeetype = employeetype;
	}

	@Column(name = "jobcode", length = 10)
	public String getJobcode() {
		return this.jobcode;
	}

	public void setJobcode(String jobcode) {
		this.jobcode = jobcode;
	}

	@Column(name = "supervisor", length = 30)
	public String getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birthdate", length = 19)
	public LocalDate getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "lastevaldate", length = 19)
	public LocalDateTime getLastevaldate() {
		return this.lastevaldate;
	}

	public void setLastevaldate(LocalDateTime lastevaldate) {
		this.lastevaldate = lastevaldate;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "nextevaldate", length = 19)
	public LocalDateTime getNextevaldate() {
		return this.nextevaldate;
	}

	public void setNextevaldate(LocalDateTime nextevaldate) {
		this.nextevaldate = nextevaldate;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "hiredate", length = 19)
	public LocalDateTime getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(LocalDateTime hiredate) {
		this.hiredate = hiredate;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "terminationdate", length = 19)
	public LocalDateTime getTerminationdate() {
		return this.terminationdate;
	}

	public void setTerminationdate(LocalDateTime terminationdate) {
		this.terminationdate = terminationdate;
	}

	@Column(name = "city", length = 36)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "regiondistrict", length = 36)
	public String getRegiondistrict() {
		return this.regiondistrict;
	}

	public void setRegiondistrict(String regiondistrict) {
		this.regiondistrict = regiondistrict;
	}

	@Column(name = "county", length = 36)
	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@Column(name = "stateprovince", length = 36)
	public String getStateprovince() {
		return this.stateprovince;
	}

	public void setStateprovince(String stateprovince) {
		this.stateprovince = stateprovince;
	}

	@Column(name = "country", length = 36)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "postalcode", length = 12)
	public String getPostalcode() {
		return this.postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mobile", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "ownergroup", length = 8)
	public String getOwnergroup() {
		return this.ownergroup;
	}

	public void setOwnergroup(String ownergroup) {
		this.ownergroup = ownergroup;
	}

	@Column(name = "siteid", length = 30)
	public String getSiteid() {
		return this.siteid;
	}

	@Column(name = "imgUrl", length = 100)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	@Transient
	public String getPasswordcheck() {
		return passwordcheck;
	}

	public void setPasswordcheck(String passwordcheck) {
		this.passwordcheck = passwordcheck;
	}
	@Transient
	public String getNp_statusmemo() {
		return np_statusmemo;
	}

	public void setNp_statusmemo(String np_statusmemo) {
		this.np_statusmemo = np_statusmemo;
	}
	@Transient
	public boolean isSynchpasswords() {
		return synchpasswords;
	}

	public void setSynchpasswords(boolean synchpasswords) {
		this.synchpasswords = synchpasswords;
	}
	@Transient
	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}
	@Transient
	public String getDbpasswordcheck() {
		return dbpasswordcheck;
	}

	public void setDbpasswordcheck(String dbpasswordcheck) {
		this.dbpasswordcheck = dbpasswordcheck;
	}
	@Transient
	public String getPasswordinput() {
		return passwordinput;
	}

	public void setPasswordinput(String passwordinput) {
		this.passwordinput = passwordinput;
	}
	@Transient
	public String getGeneratedpswd() {
		return generatedpswd;
	}

	public void setGeneratedpswd(String generatedpswd) {
		this.generatedpswd = generatedpswd;
	}
	@Transient
	public String getEmailpswd() {
		return emailpswd;
	}

	public void setEmailpswd(String emailpswd) {
		this.emailpswd = emailpswd;
	}
	@Transient
	public String getStatusiface() {
		return statusiface;
	}

	public void setStatusiface(String statusiface) {
		this.statusiface = statusiface;
	}
	@Transient
	public String getImPassword() {
		return imPassword;
	}

	public void setImPassword(String imPassword) {
		this.imPassword = imPassword;
	}

	@Column(name = "speciType", length = 20)
	public String getSpeciType() {
		return speciType;
	}

	public void setSpeciType(String speciType) {
		this.speciType = speciType;
	}
}