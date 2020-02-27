package com;

public class User {
	
	private String osUser;
	private String osRemoteHost;
	private String osDomain;
	
	
	public String getOsUser() {
		return osUser;
	}
	public void setOsUser(String osUser) {
		this.osUser = osUser;
	}
	public String getOsRemoteHost() {
		return osRemoteHost;
	}
	public void setOsRemoteHost(String osRemoteHost) {
		this.osRemoteHost = osRemoteHost;
	}
	public String getOsDomain() {
		return osDomain;
	}
	public void setOsDomain(String osDomain) {
		this.osDomain = osDomain;
	}
	
	@Override
	public String toString() {
		return "User [osUser=" + osUser + ", osRemoteHost=" + osRemoteHost + ", osDomain=" + osDomain + "]";
	}

	
	
}
