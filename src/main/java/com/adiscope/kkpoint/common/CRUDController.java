
package com.adiscope.kkpoint.common;

import com.adiscope.kkpoint.config.security.ManageBaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Component
public abstract class CRUDController<Req,Res,Entity> extends ManageBaseController implements CRUDInterface<Req,Res> {
    @Autowired(required = false)
    protected BaseService<Req,Res,Entity> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Req request) {
        log.info("{}",request);
        return baseService.create(request);
    }

    @Override
    @GetMapping("/{id}")
    public Header<Res> read(@PathVariable(name = "id") Long id) {
        log.info("read id : {}",id);
        return baseService.read(id);
    }

    @Override
    @PutMapping("") // /test/user
    public Header<Res> update(@RequestBody Req request) {
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("{id}")  // /test/user/{id}
    public Header delete(@PathVariable Long id) {
        log.info("delete : {}",id);
        return baseService.delete(id);
    }
}
