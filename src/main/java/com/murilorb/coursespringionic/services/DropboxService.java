package com.murilorb.coursespringionic.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkErrorException;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.murilorb.coursespringionic.services.exception.FileException;

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
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream is, String fileName) {
		try {
			LOG.info("Iniciando upload");
			FileMetadata metadata = dropboxClient.files().uploadBuilder("/" + fileName).withAutorename(true)
					.withMode(WriteMode.ADD).uploadAndFinish(is);
			LOG.info("Upload de arquivo finalizado");
			String url = dropboxClient.sharing().createSharedLinkWithSettings(metadata.getId()).getUrl();
			return new URI("https://dl.dropboxusercontent.com" + url.substring(23));
		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		} catch (DbxException e) {
			throw new FileException("Erro na API do Dropbox: " + e.getMessage());
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}

	public URI getFile(String fileName) {
		try {
			ListFolderResult result = dropboxClient.files().listFolder("");

			for (Metadata metadata : result.getEntries()) {
				if (metadata.getName().equalsIgnoreCase(fileName)) {
					String url = null;

					List<SharedLinkMetadata> list = dropboxClient.sharing().listSharedLinksBuilder()
							.withPath(((FileMetadata) metadata).getId()).start().getLinks();
					for (SharedLinkMetadata shared : list) {
						url = shared.getUrl();
					}

					if (url == null) {
						url = dropboxClient.sharing().createSharedLinkWithSettings(((FileMetadata) metadata).getId())
								.getUrl();
						LOG.info("URL gerada");
					}
					// cors configuration
					String newUrl = "https://dl.dropboxusercontent.com" + url.substring(23);
					return new URI(newUrl);
				}
			}
			return null;
		} catch (ListFolderErrorException e) {
			throw new FileException("Erro de ListFolderError: " + e.getMessage());
		} catch (DbxException e) {
			throw new FileException("Erro de Dbx: " + e.getMessage());
		} catch (URISyntaxException e) {
			throw new FileException("Erro de URISyntax: " + e.getMessage());
		}
	}

	public void deleteFile(String url) {
		try {
			// changing to standard dropbox url
			url = "https://www.dropbox.com" + url.substring(33);
			SharedLinkMetadata metadata = dropboxClient.sharing().getSharedLinkMetadata(url);
			dropboxClient.files().deleteV2(metadata.getId());
			LOG.info("Imagem deletada");
		} catch (SharedLinkErrorException e) {
			throw new FileException("Erro de SharedLinkError: " + e.getMessage());
		} catch (DbxException e) {
			throw new FileException("Erro de Dbx: " + e.getMessage());
		}

	}

}
