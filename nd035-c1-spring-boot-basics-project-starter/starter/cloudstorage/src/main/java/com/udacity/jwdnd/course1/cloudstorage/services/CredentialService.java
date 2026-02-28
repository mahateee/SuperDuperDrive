package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper,
                             EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentialsByUserId(Integer userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }
    public int updateCredential(Integer credentialId, String url,
                                String username, String password) {
        // generate new key for re-encryption
        String key = generateKey();
        String encryptedPassword = encryptionService.encryptValue(password, key);

        Credential credential = new Credential();
        credential.setCredentialId(credentialId);
        credential.setUrl(url);
        credential.setUsername(username);
        credential.setKey(key);
        credential.setPassword(encryptedPassword);

        return credentialMapper.update(credential);
    }
    public int createCredential(String url, String username,
                                String password, Integer userId) {
        // generate random key for encryption
        String key = generateKey();
        // encrypt password before saving
        String encryptedPassword = encryptionService.encryptValue(password, key);

        Credential credential = new Credential();
        credential.setUrl(url);
        credential.setUsername(username);
        credential.setKey(key);
        credential.setPassword(encryptedPassword);
        credential.setUserId(userId);

        return credentialMapper.insert(credential);
    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.delete(credentialId);
    }

    private String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key).substring(0, 16);
    }
}
