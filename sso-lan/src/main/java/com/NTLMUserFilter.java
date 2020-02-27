package com;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import jcifs.ntlmssp.Type3Message;

@Configuration
@Order(1)
public class NTLMUserFilter implements Filter {
	
	private FilterConfig filterConfig = null;
	private String userDomain = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String username = null;
		// first, get the user agent
		String useragent = request.getHeader("user-agent");
		// if you're using IE, you can continue
		//if ((useragent.indexOf("MSIE") > -1)) {
			// Always do the ntlm check (for IE POST back)
			try {
				String auth = request.getHeader("Authorization");
				if (auth == null) {
					response.setHeader("WWW-Authenticate", "NTLM");
					response.setStatus(response.SC_UNAUTHORIZED);
					response.setContentLength(0);
					response.flushBuffer();
					return;
				}
				if (auth.startsWith("NTLM ")) {
					byte[] msg = new sun.misc.BASE64Decoder().decodeBuffer(auth.substring(5));
					int off = 0, length, offset;
					if (msg[8] == 1) {
						byte z = 0;
						byte[] msg1 = { (byte) 'N', (byte) 'T', (byte) 'L', (byte) 'M', (byte) 'S', (byte) 'S',
								(byte) 'P', z, (byte) 2, z, z, z, z, z, z, z, (byte) 40, z, z, z, (byte) 1, (byte) 130,
								z, z, z, (byte) 2, (byte) 2, (byte) 2, z, z, z, z, z, z, z, z, z, z, z, z };
						response.setHeader("WWW-Authenticate", "NTLM " + new sun.misc.BASE64Encoder().encodeBuffer(msg1));
						response.setStatus(response.SC_UNAUTHORIZED);
						response.setContentLength(0);
						response.flushBuffer();
						return;
					} else if (msg[8] == 3) {
						// Did Authentication Succeed? All this is always printed.
						Type3Message type3 = new Type3Message(msg);
						User user = new User();
						user.setOsDomain(type3.getDomain());
						user.setOsRemoteHost(type3.getWorkstation());
						user.setOsUser(type3.getUser());
						System.out.println("osUser: " + type3.getUser());
						System.out.println("osRemoteHost: + " + type3.getWorkstation());
						System.out.println("osDomain: " + type3.getDomain());
						
			            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
			            ((HttpServletResponse) response).setStatus(200);
			            response.getOutputStream().write(user.toString().getBytes());
			            return;
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		//}

		try {
			chain.doFilter(req, res);
		} catch (IOException e) {
			System.out.println(e);
		} catch (ServletException e) {
			System.out.println(e);
		}
	}

	public void destroy() {
		this.filterConfig = null;
	}

}