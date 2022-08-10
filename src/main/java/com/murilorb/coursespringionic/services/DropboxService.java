package com.murilorb.coursespringionic.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

@Service
public class DropboxService {
	/*
	 * Fontes:
	 * 
	 * https://towardsdev.com/dropbox-api-short-lived-tokens-and-refresh-tokens-
	 * spring-java-application-fc7264dcdcbd
	 * 
	 * https://github.com/FelipeMilana/SpringBoot_Ionic-backend/commit/
	 * cec044f77f52ffbf94c005906592b58fe2608ccd#diff-
	 * c6c1d23bf12a735d0b8cdcbdba2c512ecb150568b09aecaf7ef3ad5d1ff049cb
	 */
	private Logger LOG = LoggerFactory.getLogger(DropboxService.class);

	@Autowired
	private DbxClientV2 dropboxClient;

	public void uploadFile(String pathFile) {
		try {
			LOG.info("Iniciando upload");

			InputStream inputStream = new FileInputStream(pathFile);
			Path path = Paths.get(pathFile).getFileName();
			FileMetadata metadata = dropboxClient.files().uploadBuilder("/" + path).uploadAndFinish(inputStream);

			LOG.info("Upload de arquivo para dropbox no caminho " + metadata.getPathLower() + "com nome " + path
					+ " e id " + metadata.getId());
		} catch (DbxException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

}
