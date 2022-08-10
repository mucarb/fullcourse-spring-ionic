package com.murilorb.coursespringionic.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			return uploadFile(is, fileName);
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO: " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream is, String fileName) {
		try {
			LOG.info("Iniciando upload");
			FileMetadata metadata = dropboxClient.files().uploadBuilder("/" + fileName).uploadAndFinish(is);
			LOG.info("Upload de arquivo finalizado");
			return new URI(dropboxClient.sharing().getFileMetadata(metadata.getId()).getPreviewUrl());
		} catch (DbxException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter URL para URI");
		}
	}

}
