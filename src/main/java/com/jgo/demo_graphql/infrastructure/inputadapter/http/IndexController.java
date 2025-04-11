package com.jgo.demo_graphql.infrastructure.inputadapter.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.html.HtmlRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndex(Model model) {
        String content;
        try {
            Path readmePath = new ClassPathResource("README.md").getFile().toPath();
            String markdown = Files.readString(readmePath);
            Parser parser = Parser.builder().build();
            Node document = parser.parse(markdown);
            HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
            content = htmlRenderer.render(document);
        } catch (IOException e) {
            content = "Error reading README file";
            throw new RuntimeException(e);
        }

        model.addAttribute("content", content);
        return "index";
    }

}
