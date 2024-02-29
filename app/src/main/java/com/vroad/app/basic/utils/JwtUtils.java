package com.vroad.app.basic.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {
  private static final String JWT_NAME = "username";

  public static Date getExpireDate(String jwtToken) {
    try {
      DecodedJWT jwt = JWT.decode(jwtToken);
      return jwt.getExpiresAt();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  public static String getUserName(String jwtToken) {
    try {
      DecodedJWT jwt = JWT.decode(jwtToken);
      return jwt.getClaim(JWT_NAME).asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }
}
