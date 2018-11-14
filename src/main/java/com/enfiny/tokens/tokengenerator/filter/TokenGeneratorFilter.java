package com.enfiny.tokens.tokengenerator.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <<This is the filter for angular and other front end>>
 *
 * @author Munal
 * @version 1.0.0
 * @since , 23 Feb 2018
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenGeneratorFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(TokenGeneratorFilter.class);
    private Long clientId;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> requestHeaders = new HashMap<String, String>();

        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = ((HttpServletRequest) req).getHeader(key);
            requestHeaders.put(key, value);
            System.out.println("Header------------->>>>>"+key+"-----------Value-->>>>"+value);
        }
        String originHeaders = requestHeaders.get("origin");
        response.setHeader("Access-Control-Allow-Origin", originHeaders);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,clientId,appId,grantAccessId,token,accessToken,refreshToken");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "86400");
        String clientId = request.getHeader("clientId");
        if(clientId!=null&&!clientId.equals(""))
            this.clientId = Long.parseLong(clientId);
        filterChain.doFilter(req, response);

    }

    @Override
    public void init(FilterConfig config) {
        // TODO Auto-generated method stub

    }

    public Long getClientId() {
        return clientId;
    }
}
