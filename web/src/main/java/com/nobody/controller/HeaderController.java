package com.nobody.controller;

import com.nobody.dto.UpdateOrSetHeaderDto;
import com.nobody.service.impl.UpdateHeaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/headers")
public class HeaderController {

    private UpdateHeaderServiceImpl updateHeaderService;

    @Autowired
    public HeaderController(UpdateHeaderServiceImpl updateHeaderService) {
        this.updateHeaderService = updateHeaderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UpdateOrSetHeaderDto updateHeader(@RequestBody UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        return updateHeaderService.updateHeader(updateOrSetHeaderDto);
    }


    @PostMapping("/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeHeader(@RequestBody UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        updateHeaderService.removeHeader(updateOrSetHeaderDto);
    }

    // TODO Add method for adding header
}
