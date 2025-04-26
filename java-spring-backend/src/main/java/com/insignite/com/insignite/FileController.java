package com.insignite;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {
	@Autowired
	private RestTemplate restTemplate; 

	@PostMapping("/send-file")
	public ResponseEntity<String> sendToFlask(@RequestParam("file") MultipartFile file) throws IOException {
		String url = "http://python-service:5000/analyze"; // URL of the Python API
        
        
        byte[] fileBytes = file.getBytes();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(fileBytes){
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return ResponseEntity.ok(response.getBody());
	}
}
