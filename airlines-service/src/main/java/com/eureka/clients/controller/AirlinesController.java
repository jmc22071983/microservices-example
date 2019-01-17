package com.eureka.clients.controller;

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
@Api(value="Airlines Service", tags = {"Airlines API Endpoints"})
@Produces({"application/json"})
public class AirlinesController {
	private static Cluster cluster;
	private static final Logger LOGGER = LoggerFactory.getLogger(AirlinesController.class);
	private static final String TRAVEL_SAMPLE = "travel-sample";
	private static final String PASS = "sysadmin";
	private static final String Q_AIRLINES = "SELECT id, name, country FROM `travel-sample` WHERE type = 'airline'";
	
	
	private static Bucket openBucket(String bucketName) {
		cluster = CouchbaseCluster.create("127.0.0.1:8091:8091");
		//cluster = CouchbaseCluster.create(System.getenv("COUCHBASE_ADDR"));
		return cluster.openBucket(bucketName, PASS);
	}
	
	@ApiOperation(value = "Get all airlines", produces="application/json")
	@RequestMapping(value="/airlines", method = RequestMethod.GET)
	public static String allAirlines() {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(Q_AIRLINES));
        cluster.disconnect();
		return result.allRows().toString();
	}
	
	@ApiOperation(value = "Get airline by id", produces="application/json")
	@RequestMapping(value="/airlines/{id}", method = RequestMethod.GET)
	public static String airlines(@PathVariable String id) {
		final String qAirlineById = "SELECT id, name, country FROM `travel-sample` WHERE type = 'airline' AND id = " + id;
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(qAirlineById));
        cluster.disconnect();
		return result.allRows().toString();
		
	}
}
