package com.murilorb.coursespringionic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;

@Configuration
public class DropboxConfig {
	/*
	 * Fonte utilizada para entender como foi gerado os atributos abaixos e
	 * configurados para application.properties
	 * https://towardsdev.com/dropbox-api-short-lived-tokens-and-refresh-tokens-
	 * spring-java-application-fc7264dcdcbd
	 */

	@Value("${dropbox.access.token}")
	private String dropboxAccessToken;

	@Value("${dropbox.refresh.token}")
	private String dropboxRefreshToken;

	@Value("${dropbox.app.key}")
	private String dropboxAppKey;

	@Value("${dropbox.app.secret}")
	private String dropboxAppSecret;

	@Bean
	public DbxClientV2 dropboxClient() {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("coursespringionic").build();
		DbxCredential credentials = new DbxCredential(dropboxAccessToken, -1L, dropboxRefreshToken, dropboxAppKey,
				dropboxAppSecret);
		return new DbxClientV2(config, credentials);
	}

}
