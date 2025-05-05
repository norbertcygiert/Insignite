package com.insignite;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("/")
    public ResponseEntity<Void> redirectToFrontend() {
        return ResponseEntity.status(Response.SC_MOVED_PERMANENTLY)
                .header("Location", "http://localhost:3000")
                .build();
    }
}
