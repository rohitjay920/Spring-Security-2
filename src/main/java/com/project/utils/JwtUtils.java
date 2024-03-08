package com.project.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtUtils {
	//we can give any random key
	private String jwtSecretKey="cdsiulvhebvjabvyuafhvyueabvhshruhgiudhvnkjandvkjsbvbdajfvbjsdbvjhbsvksdbvBVKSNDVKJ";

	//30 days in millis
	private Long jwtExpirationMs=30*60*60*1000l;

	public String extractUserName(String jwt) {
		return extractClaim(jwt, Claims::getSubject);
	}
	
	public String generateToken(UserDetails userDetails) {
		HashMap<String,Object> hs = new HashMap<>();
		
		System.err.println(((User)userDetails).getEmail()+" has set email in jwt");
		//setting username and role for jwt claims
		hs.put("user_name", (userDetails.getUsername()));
		hs.put("user_role",((User)userDetails).getRole());
		return generateToken(hs, userDetails);
	}

	public boolean isTokenValid(String jwt, UserDetails details) {
		String userName = extractUserName(jwt);
		System.err.println(userName+"  in jwt");
		System.err.println(details.getUsername()+"  in details");
//		System.err.println(userName.equals(details.getUsername())+"   from jwtutils");
		return (userName.equals(((User)details).getEmail()) && isTokenExpired(jwt));
	}

	private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(jwt);
		return claimsResolvers.apply(claims);
	}

	private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
	
		return Jwts.builder().setClaims(extraClaims).setSubject(((User)userDetails).getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	private boolean isTokenExpired(String jwt) {
		return extractExpiration(jwt).after(new Date());
	}

	private Date extractExpiration(String jwt) {
		return extractClaim(jwt, Claims::getExpiration);
	}

	private Claims extractAllClaims(String jwt) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt).getBody();
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
