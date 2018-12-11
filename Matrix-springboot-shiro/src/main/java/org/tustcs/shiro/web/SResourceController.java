package org.tustcs.shiro.web;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tustcs.shiro.entity.AuthResource;
import org.tustcs.shiro.pojo.ResponseBean;
import org.tustcs.shiro.service.ISResourceService;
import org.tustcs.shiro.shiro.filter.FilterManager;

@RestController
@RequestMapping("/sresource")
@Slf4j
public class SResourceController extends SBaseController {

    @Autowired
    private ISResourceService resourceService;

    @Autowired
    private FilterManager     filterManager;

    @PostMapping("/getList")
    public ResponseBean getResourceList(Integer page) {
        return new ResponseBean().ok().addData("resourceList", resourceService.getResourceList(page));
    }

    @PostMapping("/add")
    public ResponseBean addResource(@Validated @RequestBody AuthResource authResource, BindingResult errors) {
        log.info("角色：{}", authResource);
        handleError(errors);
        if (resourceService.addResource(authResource)) {
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

    @PostMapping("/update")
    public ResponseBean updateResource(@Validated @RequestBody AuthResource authResource, BindingResult errors) {
        handleError(errors);
        if (resourceService.updateResource(authResource)) {
            filterManager.reloadFilterChain();
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

    @PostMapping("/updateStatus")
    public ResponseBean deleteResource(String resources, Short status) {
        if (resourceService.updateStatus(resources, status)) {
            filterManager.reloadFilterChain();
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

}
