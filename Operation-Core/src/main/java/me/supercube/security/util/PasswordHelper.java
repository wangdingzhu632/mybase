package me.supercube.security.util;


import me.supercube.system.app.user.model.Sysuser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;


/**
 * 用户密码助手
 * */
public class PasswordHelper {


	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private final int hashIterations = 2;

	private PasswordHelper() {

	}

	public static PasswordHelper newInstance() {
		return new PasswordHelper();
	}


	/**
	 * 加密用户密码
	 * */
	public void encryptPassword(Sysuser user) {

		//设置用户密码盐值
		user.setSalt(randomNumberGenerator.nextBytes().toHex());

		//加密码后的密码
		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getSalt()),
				hashIterations).toHex();

		user.setPassword(newPassword);
	}

	/**
	 * 验证指定的密码是否相同
	 * */
	public boolean validationPassword(Sysuser user, String password) {

		String newPassword = new SimpleHash(algorithmName, password,
				ByteSource.Util.bytes(user.getSalt()),
				hashIterations).toHex();

		return newPassword.equals(user.getPassword());

	}


	public static void main(String[] args) {

		Sysuser user = new Sysuser();
		user.setUserid("admin");
		user.setPassword("admin@op[]");

		PasswordHelper helper = new PasswordHelper();
		helper.encryptPassword(user);

		System.out.println(user.getPassword());
		System.out.println(user.getSalt());
		System.out.println(user);

		boolean isValid = helper.validationPassword(user, "admin");
		System.out.println(isValid);
	}



}
