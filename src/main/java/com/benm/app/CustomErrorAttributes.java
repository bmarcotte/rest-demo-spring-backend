package com.benm.app;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
  @Autowired
  private HttpServletRequest request;

  @Override
  public Map<String, Object> getErrorAttributes( WebRequest webRequest, boolean includeStackTrace ) {
    Map<String, Object> errorAttributes = super.getErrorAttributes( webRequest, includeStackTrace );

    errorAttributes.put( "code",   errorAttributes.get( "status" ) );
    errorAttributes.put( "reason", errorAttributes.get( "error"  ) );
    errorAttributes.put( "uri",    errorAttributes.get( "path"   ) );
    errorAttributes.put( "status", "ERROR" );

    if ( request != null ) {
      errorAttributes.put( "method", request.getMethod() );
    }

    return errorAttributes;
  }
}
