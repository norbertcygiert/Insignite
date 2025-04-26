package com.insignite;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class FileAnalyzerService {
    
    private final String pythonApiUrl = "http://python-service:5000/analyze"; // URL of the Python API

    public String analyzeFile(MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultipartFile> requestEntity = new HttpEntity<>(file, headers);
        String response = restTemplate.postForObject(pythonApiUrl, requestEntity, String.class);
        return response;
    }
}
