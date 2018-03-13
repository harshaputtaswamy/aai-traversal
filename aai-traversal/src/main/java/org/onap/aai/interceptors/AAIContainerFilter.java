package org.onap.aai.interceptors;

import java.util.UUID;

import org.onap.aai.util.FormatDate;

public abstract class  AAIContainerFilter {
    
	protected String genDate() {
		FormatDate fd = new FormatDate("YYMMdd-HH:mm:ss:SSS");
		return fd.getDateTime();
	}
	
	protected boolean isValidUUID(String transId) {
		try {
			UUID.fromString(transId);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}