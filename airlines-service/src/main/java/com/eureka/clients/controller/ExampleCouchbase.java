package com.eureka.clients.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

@RestController
public class ExampleCouchbase {
	private static Cluster cluster;
	private static final String Q_AIRLINES = "SELECT id, name, country FROM `travel-sample` WHERE type = 'airline'";
	private static Bucket getBucket(String bName, String bPass) {
		//cluster = CouchbaseCluster.create("192.168.99.102:8091");
		cluster = CouchbaseCluster.create(System.getenv("COUCHBASE_ADDR"));
		return cluster.openBucket(bName, bPass);
	}

	@RequestMapping(value="/airlines/{name}", method = RequestMethod.GET)
	public static String airlines(@PathVariable String name) {
		Bucket bucket = getBucket("travel-sample", "sysadmin");
		JsonDocument jsonD = bucket.get(name);
		cluster.disconnect();
		return jsonD.content().toString();
	}
	
	@RequestMapping(value="/allAirlines", method = RequestMethod.GET)
	public static String allAirlines() {
		Bucket bucket = getBucket("travel-sample", "sysadmin");
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(Q_AIRLINES));
        cluster.disconnect();
		return result.allRows().toString();
	}
	
	@RequestMapping("/test")
	public String test() {
		return "hola";
	}

	
}
