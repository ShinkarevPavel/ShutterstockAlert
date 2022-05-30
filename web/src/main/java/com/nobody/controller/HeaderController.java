package com.nobody.controller;

import com.nobody.dto.UpdateOrSetHeaderDto;
import com.nobody.service.impl.HeaderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/headers")
public class HeaderController {
    private final Logger logger = LogManager.getLogger();

    private HeaderServiceImpl service;

    @Autowired
    public HeaderController(HeaderServiceImpl service) {
        this.service = service;
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UpdateOrSetHeaderDto updateHeader(@Validated(UpdateOrSetHeaderDto.Update.class) @RequestBody UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        logger.log(Level.INFO, "Header " + updateOrSetHeaderDto.getHeaderParameter() + " was set");
        return service.updateEntity(updateOrSetHeaderDto);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void removeHeader(@RequestBody UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        service.removeEntity(updateOrSetHeaderDto);
        logger.log(Level.INFO, "Header was removed");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addHeader(@Validated(UpdateOrSetHeaderDto.Create.class)@RequestBody UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        service.addEntity(updateOrSetHeaderDto);
        logger.log(Level.INFO, "Header was added");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UpdateOrSetHeaderDto> getAll() {
        return service.getAll();
    }

    @PostMapping("/entity")
    @ResponseStatus(HttpStatus.OK)
    public UpdateOrSetHeaderDto getHeader(@RequestBody UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        return service.getEntity(updateOrSetHeaderDto);
    }
}
