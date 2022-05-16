package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {


	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEnc = new BCryptPasswordEncoder();
		String pass="nam2020";
		String encode = passwordEnc.encode(pass);

		System.out.println(encode);
		boolean matches = passwordEnc.matches(pass, encode);
		assertThat(matches).isTrue();
	}
}
