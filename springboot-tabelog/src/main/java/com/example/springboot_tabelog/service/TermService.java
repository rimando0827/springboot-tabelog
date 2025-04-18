package com.example.springboot_tabelog.service;

import com.example.springboot_tabelog.entity.Term;

public interface TermService {
	  Term getLatestTerm();
	  String markdownToHtml(String html);
}
