package com.example.springboot_tabelog.service.impl;



import org.springframework.stereotype.Service;

import com.example.springboot_tabelog.entity.Term;
import com.example.springboot_tabelog.repository.TermRepository;
import com.example.springboot_tabelog.service.TermService;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

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
			MutableDataSet options = new MutableDataSet();
	        Parser parser = Parser.builder(options).build();
	        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
	        Node document = parser.parse(markdown);
	        String html = renderer.render(document);
	        
	        return html;
	    }

}
