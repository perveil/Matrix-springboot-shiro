package org.tustcs.shiro.shiro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.tustcs.shiro.pojo.rules.JwtRule;
import org.tustcs.shiro.service.ISAccountService;
import org.tustcs.shiro.service.ISResourceService;
import org.tustcs.shiro.util.SpringContextUtil;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lin
 */
@Component
@Slf4j
public class FilterManager {

    private final ISAccountService    accountService;

    private final StringRedisTemplate redisTemplate;

    private final ISResourceService   resourceService;

    @Autowired
    public FilterManager(ISAccountService accountService, StringRedisTemplate redisTemplate, ISResourceService resourceService) {
        this.accountService = accountService;
        this.redisTemplate = redisTemplate;
        this.resourceService = resourceService;
    }

    /**
     * 初始化过滤器，可在此扩展增加自定义的filter
     * @return
     */
    public Map<String, Filter> initFilters() {
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.setRedisTemplate(redisTemplate);
        jwtFilter.setAccountService(accountService);
        filterMap.put("jwt", jwtFilter);
        return filterMap;
    }

    /**
     * 初始化过滤器链 路径为key, 可用角色为value
     * @return
     */
    public Map<String, String> initFilterChain() {
        Map<String, String> map = new LinkedHashMap<>();
        List<JwtRule> rules = resourceService.getResourceUrlAndRoles();
        rules.forEach(rule -> {
            StringBuilder chain = rule.toFilterChain();
            if (null != chain) {
                map.putIfAbsent(rule.getUrl(), chain.toString());
            }
        });
        return map;
    }

    public void reloadFilterChain() {
        ShiroFilterFactoryBean factoryBean = SpringContextUtil.getBean(ShiroFilterFactoryBean.class);
        AbstractShiroFilter filter = null;
        try {
            filter = (AbstractShiroFilter) factoryBean.getObject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) filter.getFilterChainResolver();
        DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) resolver.getFilterChainManager();
        filterChainManager.getFilterChains().clear();
        factoryBean.getFilterChainDefinitionMap().clear();
        factoryBean.setFilterChainDefinitionMap(this.initFilterChain());
        factoryBean.getFilterChainDefinitionMap().forEach(filterChainManager::createChain);
    }
}
