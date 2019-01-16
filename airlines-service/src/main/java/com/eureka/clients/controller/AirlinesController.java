package com.eureka.clients.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;

@RestController
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
	
	@RequestMapping(value="/all-airlines", method = RequestMethod.GET)
	public static String allAirlines() {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(Q_AIRLINES));
        cluster.disconnect();
		return result.allRows().toString();
	}
	
	@RequestMapping(value="/airlines/{name}", method = RequestMethod.GET)
	public static String airlines(@PathVariable String name) {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		JsonDocument jsonD = bucket.get(name);
		cluster.disconnect();
		return jsonD.content().toString();
	}
}
