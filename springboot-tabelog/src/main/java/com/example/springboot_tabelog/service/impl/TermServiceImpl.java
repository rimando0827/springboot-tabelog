package com.example.springboot_tabelog.service.impl;



import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import com.example.springboot_tabelog.entity.Term;
import com.example.springboot_tabelog.repository.TermRepository;
import com.example.springboot_tabelog.service.TermService;

@Service
public class TermServiceImpl implements TermService {
	 private final TermRepository termRepository;
	    

	    
	    public TermServiceImpl(TermRepository termRepository) {
	        this.termRepository = termRepository;
	        
	    }

	    @Override
	    public Term getLatestTerm() {
	        // サンプルとして最初のエントリを取得
	        // 実際には、最新のものを取得するためにソートやフィルタリングを追加することを考慮する
	        return termRepository.findAll().stream().findFirst().orElse(null);
	    }

		@Override
		 public String markdownToHtml(String markdown) {
			
	        Parser parser = Parser.builder().build();
	        HtmlRenderer renderer = HtmlRenderer.builder().build();
	        Node document = parser.parse(markdown);
	        return renderer.render(document);
	        
	        
	    }

}
