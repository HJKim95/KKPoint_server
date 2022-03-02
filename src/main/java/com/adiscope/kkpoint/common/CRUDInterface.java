package com.adiscope.kkpoint.common;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CRUDInterface<Req,Res> {

    Header<Res> create(Req request);

    Header<Res> read(Long id);

    Header<Res> update(Req request);

    Header delete(Long id);
}
