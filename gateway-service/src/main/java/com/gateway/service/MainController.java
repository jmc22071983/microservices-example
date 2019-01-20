package com.gateway.service;

import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
public class MainController {
	private static Cluster cluster;

	@RequestMapping(value="/test", method = RequestMethod.GET)
	public static String airlines(@PathVariable String id) {
		return "OK";
		
	}
}
