package com.thingword.alphonso;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;



/**
 * ”¶”√
 * 
 * @author waylau.com 2014-3-18
 */
public class RestApplication extends ResourceConfig {
	public RestApplication() {
		packages("com.thingword.alphonso");
		register(MultiPartFeature.class);
		register(JacksonJsonProvider.class);	
	}
}