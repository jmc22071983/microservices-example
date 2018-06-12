package com.eureka.clients.controller;

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
		//cluster = CouchbaseCluster.create("192.168.99.100:8091");
		cluster = CouchbaseCluster.create(System.getenv("couchbase_addr"));
		/*
		 * You do not need to pass in all nodes of the cluster, just a few seed nodes so that the client is able to establish initial contact. 
		 * The actual process of connecting to a bucket (that is, opening sockets and everything related) happens when you call the openBucket method:
		 * Bucket bucket = cluster.openBucket(); This will connect to the default bucket and return a Bucket reference. 
		 * If you want to connect to a different bucket (also with a password), you can do it like this: 
		 * Bucket bucket = cluster.openBucket("bucket", "password"); 
		 */
		return cluster.openBucket(bName, bPass);
		
	}

	
	@RequestMapping(value="/airlines", method = RequestMethod.GET)
	public static String airlines() {
		Bucket bucket = getBucket("travel-sample", "sysadmin");
		JsonDocument jsonD = bucket.get("airline_10");
		cluster.disconnect();
		return jsonD.content().toString();

	}
}