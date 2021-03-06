package com.redhat.syseng.soleng.rhpam.processmigration.service;

import com.redhat.syseng.soleng.rhpam.processmigration.model.Credentials;
import org.junit.jupiter.api.Test;
import org.kie.server.client.CredentialsProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CredentialsProviderFactoryTest {

    @Test
    public void testNull() {
        String authHeader = null;
        assertNull(CredentialsProviderFactory.getCredentials(authHeader));
    }

    @Test
    public void testBasicNull() {
        String authHeader = "Basic ";
        assertNull(CredentialsProviderFactory.getCredentials(authHeader));
    }

    @Test
    public void testBearerNull() {
        String authHeader = "Bearer ";
        assertNull(CredentialsProviderFactory.getCredentials(authHeader));
    }

    @Test
    public void testBasic() {
        String authHeader = "Basic a2VybWl0OnRoZUZyb2c=";
        Credentials credentials = CredentialsProviderFactory.getCredentials(authHeader);
        assertNotNull(credentials);
        assertEquals("kermit", credentials.getUsername());
        assertEquals("theFrog", credentials.getPassword());
        assertNull(credentials.getToken());
    }

    @Test
    public void testToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.";
        String authHeader = "Bearer " + token;
        Credentials credentials = CredentialsProviderFactory.getCredentials(authHeader);
        assertNotNull(credentials);
        assertEquals(token, credentials.getToken());
        assertNull(credentials.getUsername());
        assertNull(credentials.getPassword());
    }

    @Test
    public void testProviderBasic() {
        String authHeader = "Basic a2VybWl0OnRoZUZyb2c=";
        CredentialsProvider provider = CredentialsProviderFactory.getProvider(authHeader);
        assertNotNull(provider);
        assertEquals(authHeader, provider.getAuthorization());
    }

    @Test
    public void testProviderBearer() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.";
        String authHeader = "Bearer " + token;
        CredentialsProvider provider = CredentialsProviderFactory.getProvider(authHeader);
        assertNotNull(provider);
        assertEquals(authHeader, provider.getAuthorization());
    }

    @Test
    public void testProviderBasicCredentials() {
        String authHeader = "Basic a2VybWl0OnRoZUZyb2c=";
        Credentials credentials = new Credentials().setUsername("kermit").setPassword("theFrog");
        CredentialsProvider provider = CredentialsProviderFactory.getProvider(credentials);
        assertNotNull(provider);
        assertEquals(authHeader, provider.getAuthorization());
    }

    @Test
    public void testProviderBearerCredentials() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.";
        String authHeader = "Bearer " + token;
        Credentials credentials = new Credentials().setToken(token);
        CredentialsProvider provider = CredentialsProviderFactory.getProvider(credentials);
        assertNotNull(provider);
        assertEquals(authHeader, provider.getAuthorization());
    }
}
