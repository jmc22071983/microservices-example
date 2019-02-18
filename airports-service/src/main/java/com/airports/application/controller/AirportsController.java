package com.airports.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.queries.ConjunctionQuery;
import com.couchbase.client.java.search.result.SearchQueryResult;
import com.couchbase.client.java.search.result.SearchQueryRow;
import com.couchbase.client.java.subdoc.DocumentFragment;
import com.google.gson.Gson;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Airports Service", tags = {"Airports API Endpoints"})
@Produces({"application/json"})
public class AirportsController {
	private static final Gson gson = new Gson();
	private static Cluster cluster;
	private static final Logger LOGGER = LoggerFactory.getLogger(AirportsController.class);
	private static final String TRAVEL_SAMPLE = "travel-sample";
	private static final String PASS = "sysadmin";
	private static final String Q_AIRPORTS = "SELECT id, airportname, city, country, geo FROM `travel-sample` WHERE type = 'airport'";
	
	private static Bucket openBucket(String bucketName) {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
                .connectTimeout(10000)
                .build();
		LOGGER.info("COUCHBASE_ADDR, {}", System.getenv("COUCHBASE_ADDR"));
		cluster = CouchbaseCluster.create(env,System.getenv("COUCHBASE_ADDR"));
		return cluster.openBucket(bucketName, PASS);
	}
	
	@RequestMapping(value="/airports", method = RequestMethod.GET)
	@ApiOperation(value = "Get all airports", produces="application/json")
	public static String allAirports() {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(Q_AIRPORTS));
        cluster.disconnect();
		return result.allRows().toString();
	}
	
	@GetMapping("/airports/search-airport")
	@ApiOperation(value = "Search airport by location", produces="application/json")
	public String airportByLocation(@RequestParam String location) {  
		List<Map<String,String>> sresult = new ArrayList<>();
		ConjunctionQuery fts = SearchQuery.conjuncts(SearchQuery.term("airport").field("type"));
	    fts.and(
	    		SearchQuery.disjuncts(
	    		SearchQuery.matchPhrase(location).field("country"),
	    		SearchQuery.matchPhrase(location).field("city")
	    		)
	    		);
	    Bucket bucket = openBucket(TRAVEL_SAMPLE);
	    SearchQuery query = new SearchQuery("travel", fts).limit(100);
	    SearchQueryResult result = bucket.query(query);	   
	    for (SearchQueryRow row : result) {
	        DocumentFragment<Lookup> fragment = bucket
	                .lookupIn(row.id())
	                .get("airportname")
	                .get("city")
	                .execute();
	        Map<String,String> map = new HashMap<>();
	        map.put("id",row.id());
	        if(fragment.content(0) != null) {
	        	 map.put("airportname", fragment.content(0).toString());	
	        }
	        if(fragment.content(1) != null) {
	        	 map.put("city", fragment.content(1).toString());
	        }
	       
	        sresult.add(map);
	    }
	   
	    return  gson.toJson(sresult);
	}
}
