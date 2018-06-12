package com.eureka.clients;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Airlines")
public class Airlines {
	protected String content;

	public Airlines(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
