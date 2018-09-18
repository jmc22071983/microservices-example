package com.eureka.clients.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;

@RestController
public class ExampleCouchbase {
	private static Cluster cluster;
	private static Bucket getBucket(String bName, String bPass) {
		/*
		 * The first thing you need to do is connect to the cluster: 
		 */
		cluster = CouchbaseCluster.create("127.0.0.1:8091");
		//cluster = CouchbaseCluster.create(System.getenv("couchbase_addr"));
		/*
		 * You do not need to pass in all nodes of the cluster, just a few seed nodes so that the client is able to establish initial contact. 
		 * The actual process of connecting to a bucket (that is, opening sockets and everything related) happens when you call the openBucket method:
		 * Bucket bucket = cluster.openBucket(); This will connect to the default bucket and return a Bucket reference. 
		 * If you want to connect to a different bucket (also with a password), you can do it like this: 
		 * Bucket bucket = cluster.openBucket("bucket", "password"); 
		 */
		return cluster.openBucket(bName, bPass);
		
	}
	
	@RequestMapping("/test")
	public String test() {
		return "hola";
	}

	
	@RequestMapping(value="/airlines/{name}", method = RequestMethod.GET)
	public static String airlines(@PathVariable String name) {
		Bucket bucket = getBucket("travel-sample", "sysadmin");
		JsonDocument jsonD = bucket.get(name);
		cluster.disconnect();
		return jsonD.content().toString();
	}
	
	@RequestMapping(value="/airlines/", method = RequestMethod.GET)
	public static String allAirlines() {
		Bucket bucket = getBucket("travel-sample", "sysadmin");
		// Create a N1QL Primary Index (but ignore if it exists)
        bucket.bucketManager().
		return null
	}
}
