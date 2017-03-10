package me.supercube.system.app.site.model;
// Generated 2016-9-13 11:32:38 by Hibernate Tools 4.3.1.Final

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.json.HTTP;
import org.omg.PortableInterceptor.ACTIVE;
import org.omg.PortableInterceptor.INACTIVE;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import static com.sun.tools.doclint.Entity.or;

/**
 * 系统信息
 * Sysinfo generated by hbm2java
 */
@Entity
@Table(name = "sysinfo")
public class Sysinfo implements java.io.Serializable {

	private String orgid;//平台ID
	private Integer version;//系统版本
	private String sysname;//系统名称
	private String title;//显示标题	title	显示标题
	private String longtitle;//长标题
	private String description;//系统描述
	private String shortcut;//显示图标
	private String homeurl;//主页URL
	private String logourl;//LOGO地址
	private String loginbg;//登陆页面背景图
	private String copyright;//版权信息
	private String icp;//备案号
	private String master;//管理员姓名
	private String masteremail;//管理员email
	private String status;//系统状态,系统状态(ACTIVE or INACTIVE or DEBUG)
	private String weatherapi;//天气API,天气API
	private String weatherapikey;//天气API KEY,天气API KEY
	private Integer timezone;//时区,
	private Integer useProxy;//启用代理,启用代理 0不启用代理 1启用代理
	private String protocol;//代理协议,代理协议 protocol HTTP、SOCKS
	private String proxyHost;//代理服务器IP,代理服务器IP
	private Integer proxyPort;//代理服务器端口	,代理服务器端口
	private Boolean validate;//代理服务需要验证,代理服务需要验证
	private String proxyUser;//代理服务器登陆用户名,代理服务器登陆用户名
	private String proxyPassword;//代理服务器登陆密码,代理服务器登陆密码
	private LocalDateTime installdate;//安装日期,安装日期
	private LocalDateTime rundate;//正式运行日期,正式运行日期

	public Sysinfo() {
	}

	public Sysinfo(String orgid) {
		this.orgid = orgid;
	}

	public Sysinfo(String orgid, String sysname, String title, String longtitle, String description, String shortcut,
			String homeurl, String logourl, String loginbg, String copyright, String icp, String master,
			String masteremail, String status, String weatherapi, String weatherapikey, Integer timezone,
			Integer useProxy, String protocol, String proxyHost, Integer proxyPort, Boolean validate, String proxyUser,
			String proxyPassword, LocalDateTime installdate, LocalDateTime rundate) {
		this.orgid = orgid;
		this.sysname = sysname;
		this.title = title;
		this.longtitle = longtitle;
		this.description = description;
		this.shortcut = shortcut;
		this.homeurl = homeurl;
		this.logourl = logourl;
		this.loginbg = loginbg;
		this.copyright = copyright;
		this.icp = icp;
		this.master = master;
		this.masteremail = masteremail;
		this.status = status;
		this.weatherapi = weatherapi;
		this.weatherapikey = weatherapikey;
		this.timezone = timezone;
		this.useProxy = useProxy;
		this.protocol = protocol;
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		this.validate = validate;
		this.proxyUser = proxyUser;
		this.proxyPassword = proxyPassword;
		this.installdate = installdate;
		this.rundate = rundate;
	}

	@Id

	@Column(name = "orgid", unique = true, nullable = false, length = 30)
	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "sysname", length = 20)
	public String getSysname() {
		return this.sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	@Column(name = "title", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "longtitle", length = 200)
	public String getLongtitle() {
		return this.longtitle;
	}

	public void setLongtitle(String longtitle) {
		this.longtitle = longtitle;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "shortcut", length = 200)
	public String getShortcut() {
		return this.shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	@Column(name = "homeurl", length = 200)
	public String getHomeurl() {
		return this.homeurl;
	}

	public void setHomeurl(String homeurl) {
		this.homeurl = homeurl;
	}

	@Column(name = "logourl", length = 200)
	public String getLogourl() {
		return this.logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	@Column(name = "loginbg", length = 200)
	public String getLoginbg() {
		return this.loginbg;
	}

	public void setLoginbg(String loginbg) {
		this.loginbg = loginbg;
	}

	@Column(name = "copyright", length = 200)
	public String getCopyright() {
		return this.copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	@Column(name = "icp", length = 100)
	public String getIcp() {
		return this.icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}

	@Column(name = "master", length = 50)
	public String getMaster() {
		return this.master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Column(name = "masteremail", length = 100)
	public String getMasteremail() {
		return this.masteremail;
	}

	public void setMasteremail(String masteremail) {
		this.masteremail = masteremail;
	}

	@Column(name = "status", length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "weatherapi", length = 200)
	public String getWeatherapi() {
		return this.weatherapi;
	}

	public void setWeatherapi(String weatherapi) {
		this.weatherapi = weatherapi;
	}

	@Column(name = "weatherapikey", length = 100)
	public String getWeatherapikey() {
		return this.weatherapikey;
	}

	public void setWeatherapikey(String weatherapikey) {
		this.weatherapikey = weatherapikey;
	}

	@Column(name = "timezone")
	public Integer getTimezone() {
		return this.timezone;
	}

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}

	@Column(name = "useProxy")
	public Integer getUseProxy() {
		return this.useProxy;
	}

	public void setUseProxy(Integer useProxy) {
		this.useProxy = useProxy;
	}

	@Column(name = "protocol", length = 20)
	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Column(name = "proxyHost", length = 50)
	public String getProxyHost() {
		return this.proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	@Column(name = "proxyPort")
	public Integer getProxyPort() {
		return this.proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	@Column(name = "validate")
	public Boolean getValidate() {
		return this.validate;
	}

	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	@Column(name = "proxyUser", length = 50)
	public String getProxyUser() {
		return this.proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	@Column(name = "proxyPassword", length = 50)
	public String getProxyPassword() {
		return this.proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "installdate", length = 19)
	public LocalDateTime getInstalldate() {
		return this.installdate;
	}

	public void setInstalldate(LocalDateTime installdate) {
		this.installdate = installdate;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "rundate", length = 19)
	public LocalDateTime getRundate() {
		return this.rundate;
	}

	public void setRundate(LocalDateTime rundate) {
		this.rundate = rundate;
	}

}