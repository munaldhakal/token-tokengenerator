package com.enfiny.tokens.tokengenerator.controller;


import com.enfiny.tokens.tokengenerator.request.ClientCreationDto;
import com.enfiny.tokens.tokengenerator.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/createClient", method = RequestMethod.POST)
    public ResponseEntity<Object> createClient(@RequestBody ClientCreationDto dto) {
        clientService.createClient(dto);

        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
}
